package com.progressoft.jipfive.gsodcommands.model;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.json.handler.JsonHandler;

public class SummeryByCodeAndDateCommand implements Command {

	private GetGsodSummery summery;
	private JsonHandler<List<GsodSummery>> handler;

	public SummeryByCodeAndDateCommand(GetGsodSummery summery, JsonHandler<List<GsodSummery>> handler) {
		this.summery = summery;
		this.handler = handler;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<GsodSummery> summeryByDateRange = summery.getSummeryList(request, response);
		handler.writeJson(summeryByDateRange, response);
		
//		Gson converter = new Gson();
//		String json = converter.toJson(summeryByDateRange);
//		PrintWriter writer = response.getWriter();
//		response.setContentType("application/json");
//		writer.write(json);
//		writer.flush();
	}

}
