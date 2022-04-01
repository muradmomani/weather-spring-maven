//package com.progressoft.jipfive;
//
//import java.io.File;
//import java.sql.Connection;
//import java.util.Scanner;
//
//import com.progressoft.jip.FileGsodDAO;
////import com.progrssoft.jipfive.MenuCompsite;
//
//public class Main {
//	public static File dirName = new File("/home/u687/noaa");
//	public static File dirNameGsod = new File("/home/u687/noaa/gsod");
//
//	public static void main(String[] args) {
//		Factory<Connection> connectionFactory = preparingConnectionFactory();
//
//		// Preparing StationDataBaseDao
//		StationsDataBaseDAO stationDataBaseDAO = prepareStationsDAO(connectionFactory);
//		importStationstoDB(connectionFactory);
//
//		// Station DAO && Operations
//		StationOperations stationsOperations = new StationOperations(stationDataBaseDAO);
//		// from here to up is dependency injection example
//		// gsod DataStore && DAO && Operations
//		GsodOperations gsodOperations = prepareGsodOperations(connectionFactory, dirNameGsod, stationDataBaseDAO);
//
//		// prepare Import and status :
//		ImportAndStatusOperation importAndStatusOperations = prepareImportOp(connectionFactory);
//
//		// Menu menuManager = new MenuCompsite(stationsOperations, gsodOperations,
//		// importAndStatusOperations);
//		Scanner in = new Scanner(System.in);
//		// runProgram(in, menuManager);
//		return;
//	}
//
//	// private static void runProgram(Scanner in, Menu menuManager) {
//	// while (true) {
//	// menuManager.displayOptions();
//	// String line = in.nextLine();
//	// try {
//	// int key = Integer.valueOf(line.trim());
//	// menuManager.executeOption(key);
//	// } catch (Exception ex) {
//	// System.err.println(ex.getMessage());
//	// }
//	// }
//	// }
//	//
//	private static StationsDataBaseDAO prepareStationsDAO(Factory<Connection> connectionFactory) {
//		DBDataStore<Station> stationDBQueryExecutor = new DBDataStore<>(new DataBaseStationParser(), connectionFactory);
//
//		StationsDataBaseDAO stationDataBaseDAO = new StationsDataBaseDAO(stationDBQueryExecutor);
//
//		return stationDataBaseDAO;
//	}
//
//	// private static StationDAO prepareStationsDao(File dirName) {
//	// DataStore fileDataStore = new FileDataStore(dirName);
//	// Parser<Station, String> parser = new StringToStationParser();
//	// return new FileStationsDAO(fileDataStore, parser);
//	// }
//
//	private static GsodOperations prepareGsodOperations(Factory<Connection> connectionFactory, File dirName,
//			StationDAO stationsDao) {
//
//		DBQueryExecutor<Gsod> gsodDBQueryExecutor = new DBDataStore<>(new DataBaseGsodParser(), connectionFactory);
//		// preparing file and database datastore
//		Parser<Gsod, String> parser = new StringToGsodParser();
//		DataStore<Gsod> gsodDataStor = new FileDataStore(dirNameGsod, parser);
//		GsodDAO fileGsodDAO = new FileGsodDAO(gsodDataStor);
//		GsodDAO dataBaseGsodDAO = new GSODDataBaseDAO(gsodDBQueryExecutor, connectionFactory);
//		// prepare the decorator Dao
//		GsodDAO gsodDecoratorDAO = new GsodDecoratorDAO(fileGsodDAO, dataBaseGsodDAO);
//		// Return the Gsod Operations
//		return new GsodOperations(gsodDecoratorDAO, stationsDao);
//	}
//
//	private static ImportAndStatusOperations prepareImportOp(Factory<Connection> connectionFactory) {
//		Parser<Gsod, String> parser = new StringToGsodParser();
//		// DataStore gsodDataStor = new FileDataStore(new
//		// File(Configurations.GSOD_DIRECTORY));
//		DataStore<Gsod> gsodDataStor = new FileDataStore(dirNameGsod, parser);
//
//		GsodDAO fileGsodDAO = new FileGsodDAO(gsodDataStor);
//
//		ImportAndStatusOperations importAndStatusOperations = null;// new ImportAndStatusOperations(parser, fileGsodDAO,
//																	// connectionFactory);
//		return importAndStatusOperations;
//
//	}
//
//	private static void importStationstoDB(Factory<Connection> connectionFactory) {
//		Parser<Station, String> stringToStationParser = new StringToStationParser();
//		StationsImporter stationsImporter = new StationsImporter(connectionFactory, stringToStationParser);
//		Thread stationsToDatabaseThread = new Thread(stationsImporter::writeStationsToDataBase);
//		stationsToDatabaseThread.start();
//	}
//
//	private static Factory<Connection> preparingConnectionFactory() {
//		String url = "jdbc:mysql://localhost:3306/weather?useSSL=false";
//		String userName = "root";
//		String passwd = "root";
//		return new DBConnectionFactory(url, userName, passwd);
//	}
//}
//
//// Note
//// introduce StationsDao interface
//// write implementation of stationsDao (MemoryStationsDao)
//// List<stations> searchByStationId(String id, String wban)
//// then pass the dao to the stations operations
