package com.progressof.jipfive.gsodcommands.view;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;
import com.progressoft.jipfive.usecases.spi.StationUseCases;


public class GsodMainViewCopy implements Command {

	private StationUseCases stationUasecase;
	private GsodUseCase gsodUseCase;

	public GsodMainViewCopy(StationUseCases stationUasecase, GsodUseCase gsodUseCase) {
		this.stationUasecase = stationUasecase;
		this.gsodUseCase = gsodUseCase;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<String> startDates = gsodUseCase.getAvilableDates();
		startDates = startDates.stream().map(d -> d.substring(0, 4) + "/" + d.substring(4, 6) + "/" + d.substring(6, 8))
				.sorted().collect(Collectors.toList());

		Collection<String> countries = stationUasecase.getAllCountries();

		request.setAttribute("countries", countries);
		request.setAttribute("startDates", startDates);

		request.getRequestDispatcher("/gsodView/GsodMainViewCopy.jsp").forward(request, response);

	}

}
