package com.progressoft.jipfive.stationscommands.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.param.reader.web.StationWebParamReader;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class ListStateCommand implements Command {

	private StationUseCases useCases;

	public ListStateCommand(StationUseCases useCases) {
		this.useCases = useCases;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		StationWebParamReader reader = new StationWebParamReader(request);
		Collection<Station> searchByCountryCode = useCases.searchByCountryCode(reader);

		response.setContentType("application/json");
		Gson gson = new Gson();
		String toBeReturned = gson.toJson(searchByCountryCode);
		PrintWriter writer = response.getWriter();
		writer.write(toBeReturned);
		writer.flush();

	}

}
