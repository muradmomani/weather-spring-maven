package com.progressoft.jipfive.gsodcommands.model;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.json.handler.JsonHandler;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;

public class EndDatesAsJsonCommand implements Command {

	private GsodUseCase uasecase;
	private JsonHandler<List<String>> handler;

	public EndDatesAsJsonCommand(GsodUseCase uasecase, JsonHandler<List<String>> hanler) {
		this.uasecase = uasecase;
		this.handler = hanler;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");
		List<String> dates = uasecase.getAvilableDates();
		dates = dates
				.stream()
				.sorted()
				.map(d -> d.substring(0, 4) + "/" + d.substring(4, 6) + "/" + d.substring(6, 8))
				.filter(d -> {
					return d.compareTo(date) >= 0;
				}).collect(Collectors.toList());
		handler.writeJson(dates, response);

	}

	// private void writeJsonResponse(HttpServletResponse response, List<String>
	// dates) throws IOException {
	// Gson converter = new Gson();
	// String json = converter.toJson(dates);
	// PrintWriter writer = response.getWriter();
	// response.setContentType("application/json");
	// writer.write(json);
	// writer.flush();
	// }

}
