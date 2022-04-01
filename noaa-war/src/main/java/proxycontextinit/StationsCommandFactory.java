package proxycontextinit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.progressoft.jipfive.command.ViewCommand;
import com.progressoft.jipfive.commandfactory.CommandFactory;
import com.progressoft.jipfive.json.handler.JsonHandler;
import com.progressoft.jipfive.stationscommands.model.LatestGsodRecord;
import com.progressoft.jipfive.stationscommands.model.ListStateCommand;
import com.progressoft.jipfive.stationscommands.model.StatesAsJsonCommand;
import com.progressoft.jipfive.stationscommands.model.StationByCountryCommand;
import com.progressoft.jipfive.stationscommands.model.StationsByGeographicalRangeCommand;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class StationsCommandFactory {

	private StationUseCases stationUseCases;

	private GsodUseCase gsodUseCase;

	public StationsCommandFactory(StationUseCases stationUseCases, GsodUseCase gsodUseCase) {
		super();
		this.stationUseCases = stationUseCases;
		this.gsodUseCase = gsodUseCase;
	}

	public CommandFactory build() {
		CommandFactory commandFactory = new CommandFactory();

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

		return commandFactory;
	}
}
