package com.progressoft.jipfive;

import com.progressoft.jip.FileGsodDAO;
import com.progressoft.jipfive.consol.operation.imp.GsodOperations;
import com.progressoft.jipfive.consol.operation.imp.ImportAndStatusOperations;
import com.progressoft.jipfive.consol.operation.imp.StationOperations;
import com.progressoft.jipfive.consol.operations.GSODOperation;
import com.progressoft.jipfive.consol.operations.ImportAndStatusOperation;
import com.progressoft.jipfive.consol.operations.StationOperationsInterface;
import com.progressoft.jipfive.usecases.imp.DefaultGsodUseCase;
import com.progressoft.jipfive.usecases.imp.DefaultImportUseCase;
import com.progressoft.jipfive.usecases.imp.DefaultStationUseCases;
import com.progressoft.jipfive.usecases.spi.ImportUseCase;
import com.progressoft.jipfive.usecases.spi.StationUseCases;
import com.progrssoft.jipfive.MenuCompsite;

import java.io.File;
import java.sql.Connection;
import java.util.Scanner;
public class MainConsol {
	public static File dirName = new File("/home/u687/noaa");
	public static File dirNameGsod = new File("/home/u687/noaa/gsod");

	public static void main(String[] args) {

		Factory<Connection> connectionFactory = preparingConnectionFactory();

		StationsDataBaseDAO stationDataBaseDAO = prepareStationsDAO(connectionFactory);
		importStationstoDB(connectionFactory);

		StationUseCases stationUseCases = new DefaultStationUseCases(stationDataBaseDAO);
		StationOperationsInterface stationsOperations = new StationOperations(stationUseCases);

		GSODOperation gsodOperations = prepareGsodOperations(connectionFactory, dirNameGsod, stationDataBaseDAO);

		ImportAndStatusOperation importAndStatusOperations = prepareImportOp(connectionFactory);

		Menu menuManager = new MenuCompsite(stationsOperations, gsodOperations, importAndStatusOperations);
		Scanner in = new Scanner(System.in);
		runProgram(in, menuManager);
		return;
	}

	private static void runProgram(Scanner in, Menu menuManager) {
		while (true) {
			menuManager.displayOptions();
			String line = in.nextLine();
			try {
				int key = Integer.valueOf(line.trim());
				menuManager.executeOption(key);
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	private static StationsDataBaseDAO prepareStationsDAO(Factory<Connection> connectionFactory) {
		DBDataStore<Station> stationDBQueryExecutor = new DBDataStore<>(new DataBaseStationParser(),
				connectionFactory);

		StationsDataBaseDAO stationDataBaseDAO = new StationsDataBaseDAO(stationDBQueryExecutor);

		return stationDataBaseDAO;
	}

	private static GsodOperations prepareGsodOperations(Factory<Connection> connectionFactory, File dirName,
			StationDAO stationsDao) {

		DBQueryExecutor<Gsod> gsodDBQueryExecutor = new DBDataStore<>(new DataBaseGsodParser(), connectionFactory);
		// preparing file and database datastore
		Parser<Gsod, String> parser = new StringToGsodParser();
		DataStore<Gsod> gsodDataStor = new FileDataStore<Gsod>(dirNameGsod, parser);
		GsodDAO fileGsodDAO = new FileGsodDAO(gsodDataStor);
		GsodDAO dataBaseGsodDAO = new GSODDataBaseDAO(gsodDBQueryExecutor, connectionFactory);
		// prepare the decorator Dao
		GsodDAO gsodDecoratorDAO = new GsodDecoratorDAO(fileGsodDAO, dataBaseGsodDAO);
		// Return the Gsod Operations
		DefaultGsodUseCase defaultGsodUseCase = new DefaultGsodUseCase(gsodDecoratorDAO, stationsDao);
		return new GsodOperations(defaultGsodUseCase);
	}

	private static ImportAndStatusOperations prepareImportOp(Factory<Connection> connectionFactory) {
		Parser<Gsod, String> parser = new StringToGsodParser();
		// DataStore gsodDataStor = new FileDataStore(new
		// File(Configurations.GSOD_DIRECTORY));
		DataStore<Gsod> gsodDataStor = new FileDataStore<Gsod>(dirNameGsod, parser);

		GsodDAO fileGsodDAO = new FileGsodDAO(gsodDataStor);
		ImportUseCase importUseCase = new DefaultImportUseCase(parser, fileGsodDAO, connectionFactory);
		ImportAndStatusOperations importAndStatusOperations = new ImportAndStatusOperations(importUseCase);
		return importAndStatusOperations;

	}

	private static void importStationstoDB(Factory<Connection> connectionFactory) {
		Parser<Station, String> stringToStationParser = new StringToStationParser();
		StationsImporter stationsImporter = new StationsImporter(connectionFactory, stringToStationParser);
		Thread stationsToDatabaseThread = new Thread(stationsImporter::writeStationsToDataBase);
		stationsToDatabaseThread.start();
	}

	private static Factory<Connection> preparingConnectionFactory() {
		String url = "jdbc:mysql://localhost:3306/weather?useSSL=false";
		String userName = "root";
		String passwd = "root";
		return new DBConnectionFactory(url, userName, passwd);
	}
}