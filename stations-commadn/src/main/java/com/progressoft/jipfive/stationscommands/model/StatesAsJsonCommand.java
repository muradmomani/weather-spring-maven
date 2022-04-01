package com.progressoft.jipfive.stationscommands.model;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.json.handler.JsonHandler;
import com.progressoft.jipfive.param.reader.web.StationWebParamReader;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class StatesAsJsonCommand implements Command {

	private StationUseCases stationUseCase;
	private JsonHandler<Collection<String>> handler;

	public StatesAsJsonCommand(StationUseCases stationUseCase, JsonHandler<Collection<String>> handler) {
		this.stationUseCase = stationUseCase;
		this.handler = handler;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StationWebParamReader reader = new StationWebParamReader(request);
		Collection<String> allStates = stationUseCase.getAllStates(reader);
		handler.writeJson(allStates, response);
		// writeJsonResponse(response, allStates);

	}
//
//	private void writeJsonResponse(HttpServletResponse response, Collection<String> allStates) throws IOException {
//		Gson stateAsJson = new Gson();
//		String jsonElements = stateAsJson.toJson(allStates);
//		response.setContentType("application/json");
//		PrintWriter writer = response.getWriter();
//		writer.write(jsonElements);
//	}

}
