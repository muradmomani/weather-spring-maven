package com.progressoft.jipfive.stationscommands.model;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class StationByCountryCommand implements Command {

	private StationUseCases stationUseCase;

	public StationByCountryCommand(StationUseCases stationUseCase) {
		this.stationUseCase = stationUseCase;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Collection<String> countries =  stationUseCase.getAllCountries();
		if (!countries.isEmpty())
			request.setAttribute("countries", countries);
		request.getRequestDispatcher("/WEB-INF/StationCountryView.jsp").forward(request, response);
	}

}
