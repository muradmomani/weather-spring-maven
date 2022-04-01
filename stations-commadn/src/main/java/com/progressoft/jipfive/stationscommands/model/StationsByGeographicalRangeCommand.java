package com.progressoft.jipfive.stationscommands.model;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.param.reader.web.StationWebParamReader;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class StationsByGeographicalRangeCommand implements Command {
	private StationUseCases useCases;

	public StationsByGeographicalRangeCommand(StationUseCases useCases) {
		this.useCases = useCases;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StationWebParamReader reader = new StationWebParamReader(request);
		Collection<Station> searchWithinradius = useCases.searchWithinradius(reader);
		request.setAttribute("stations", searchWithinradius);
		request.getRequestDispatcher("/WEB-INF/StationRangeTableView.jsp").forward(request, response);

	}

}
