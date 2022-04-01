package proxycontextinit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.progressoft.jipfive.GsodProxy;
import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.ImportProxy;
import com.progressoft.jipfive.StationsProxy;
import com.progressoft.jipfive.command.ViewCommand;
import com.progressoft.jipfive.commandfactory.CommandFactory;
import com.progressoft.jipfive.gsodcommands.model.DownloadCSVCommand;
import com.progressoft.jipfive.gsodcommands.model.EndDatesAsJsonCommand;
import com.progressoft.jipfive.gsodcommands.model.GetGsodSummery;
import com.progressoft.jipfive.gsodcommands.model.GetNumOfSummeryRecordsCommand;
import com.progressoft.jipfive.gsodcommands.model.SummeryByCodeAndDateCommand;
import com.progressoft.jipfive.importcommands.model.ImportGsodFileCommand;
import com.progressoft.jipfive.importcommands.model.ImportGsodFileReportCommand;
import com.progressoft.jipfive.json.handler.JsonHandler;
import com.progressoft.jipfive.paramreaders.spi.ImportParamReader;
import com.progressoft.jipfive.recovery.thread.RecoveryThread;
import com.progressoft.jipfive.stationscommands.model.LatestGsodRecord;
import com.progressoft.jipfive.stationscommands.model.ListStateCommand;
import com.progressoft.jipfive.stationscommands.model.StatesAsJsonCommand;
import com.progressoft.jipfive.stationscommands.model.StationByCountryCommand;
import com.progressoft.jipfive.stationscommands.model.StationsByGeographicalRangeCommand;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;
import com.progressoft.jipfive.usecases.spi.GsodUseCaseWithPagining;
import com.progressoft.jipfive.usecases.spi.ImportUseCase;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class UpdatedContextInitilizer implements ServletContainerInitializer {
	private File dirNameGsod = new File("/home/u687/noaa/gsod");
	private File backupFile = new File("/home/u687/noaa/backUp.txt");

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/context.xml");

		StationUseCases stationUseCase = (StationUseCases) context.getBean("stationUsecase");
		GsodUseCase gsodUseCase = (GsodUseCase) context.getBean("gsodUsecase");
		ImportUseCase importUseCase = (ImportUseCase) context.getBean("importUseCase");
		GsodUseCaseWithPagining gsodWithPageining = (GsodUseCaseWithPagining) context.getBean("gsodWithPageining");
		GetGsodSummery summery = (GetGsodSummery) context.getBean("summery");

		CommandFactory stationCmmandFactory = (CommandFactory) context.getBean("stationsCommandFactory");
		CommandFactory gsodCommandFactory = new CommandFactory();
		CommandFactory importCommandFactory = new CommandFactory();

		addCommandsToGsodFactory(gsodCommandFactory, gsodUseCase, summery, gsodWithPageining, stationUseCase);
		addCommandsToImportFactory(importCommandFactory, importUseCase);

		StationsProxy stationsProxyServlet = new StationsProxy(stationCmmandFactory);
		Dynamic stationDynamic = ctx.addServlet("stationsProxyServlet", stationsProxyServlet);
		stationDynamic.addMapping("/noaa/stations/*");

		GsodProxy gsodProxyServlet = new GsodProxy(gsodCommandFactory);
		Dynamic gsodDynamic = ctx.addServlet("gsodProxyServlet", gsodProxyServlet);
		gsodDynamic.addMapping("/noaa/gsod/*");

		ImportProxy importProxyServlet = new ImportProxy(importCommandFactory);
		Dynamic importDynamic = ctx.addServlet("HttpProxy", importProxyServlet);
		importDynamic.addMapping("/noaa/import/*");

		RecoveryThread recoverThread = prepareRecovryThread();
		recoverThread.setListOfFile(importUseCase.getFilesInTheThread());
		recovreGsodFiles(importUseCase);

	}

	private void addCommandsToImportFactory(CommandFactory commandFactory, ImportUseCase importUseCase) {
		commandFactory.addCommand("/main/view", new ViewCommand("/WEB-INF/gsodImportViews/ImporterMainView.jsp", r -> {
			List<String> dates = importUseCase.getAvilableFiles().stream().sorted().collect(Collectors.toList());
			r.setAttribute("dates", dates);
		}));
		commandFactory.addCommand("/importGsod/thread", new ImportGsodFileCommand(importUseCase));
		commandFactory.addCommand("/importGsod/rebort", new ImportGsodFileReportCommand(importUseCase));

	}

	private void addCommandsToGsodFactory(CommandFactory commandFactory, GsodUseCase gsod, GetGsodSummery summery,
			GsodUseCaseWithPagining gsodWithPageining, StationUseCases station) {
		commandFactory.addCommand("/main/view", new ViewCommand("/WEB-INF/gsodView/GsodMainViewCopy.jsp", r -> {
			List<String> startDates = gsod.getAvilableDates();
			startDates = startDates.stream()
					.map(d -> d.substring(0, 4) + "/" + d.substring(4, 6) + "/" + d.substring(6, 8)).sorted()
					.collect(Collectors.toList());

			Collection<String> countries = station.getAllCountries();

			r.setAttribute("countries", countries);
			r.setAttribute("startDates", startDates);

		}));

		commandFactory.addCommand("/filter/date", new EndDatesAsJsonCommand(gsod, new JsonHandler<List<String>>()));
		commandFactory.addCommand("/filter/country",
				new SummeryByCodeAndDateCommand(summery, new JsonHandler<List<GsodSummery>>()));
		commandFactory.addCommand("/filter/download", new DownloadCSVCommand(summery));
		commandFactory.addCommand("/filter/numOfRecords", new GetNumOfSummeryRecordsCommand(gsodWithPageining));

	}

	private void addCommandsToStationsFactory(CommandFactory commandFactory, StationUseCases stationUseCases,
			GsodUseCase gsodUseCase, ImportUseCase importOperation, GsodUseCaseWithPagining gsodUasecaseWithPaginig,
			GetGsodSummery summery) {

		commandFactory.addCommand("/mainview", new ViewCommand("/WEB-INF/mainview.jsp", r -> {
		}));
		commandFactory.addCommand("/main/view", new ViewCommand("/WEB-INF/StationMainView.jsp", r -> {
		}));
		commandFactory.addCommand("/filter/range", new ViewCommand("/WEB-INF/StationRangeView.jsp", r -> {
		}));
		commandFactory.addCommand("/filter/country", new StationByCountryCommand(stationUseCases));
		commandFactory.addCommand("/filter/state",
				new StatesAsJsonCommand(stationUseCases, new JsonHandler<Collection<String>>()));
		commandFactory.addCommand("/state/model", new ListStateCommand(stationUseCases));
		commandFactory.addCommand("/GsodLatestRecord/view", new LatestGsodRecord(gsodUseCase));
		commandFactory.addCommand("/filter/range/model", new StationsByGeographicalRangeCommand(stationUseCases));

	}

	private RecoveryThread prepareRecovryThread() {
		RecoveryThread recoverThread = new RecoveryThread();
		recoverThread.setDirNAme(Paths.get(dirNameGsod.getParent()));
		Runtime runTime = Runtime.getRuntime();
		runTime.addShutdownHook(new Thread(recoverThread));
		return recoverThread;
	}

	private void recovreGsodFiles(ImportUseCase importUsecase) {
		try (BufferedReader br = new BufferedReader(new FileReader(backupFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				final String temp = line.trim();

				ImportParamReader reader = new ImportParamReader() {
					@Override
					public String getFileName() {
						return temp;
					}
				};
				importUsecase.importGsodFile(reader);
			}
			Files.delete(backupFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
