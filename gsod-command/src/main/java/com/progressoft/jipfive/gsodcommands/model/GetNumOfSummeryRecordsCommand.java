package com.progressoft.jipfive.gsodcommands.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.param.reader.web.GsodWebParamreader;
import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;
import com.progressoft.jipfive.usecases.spi.GsodUseCaseWithPagining;

public class GetNumOfSummeryRecordsCommand implements Command {

	private GsodUseCaseWithPagining uasecase;

	public GetNumOfSummeryRecordsCommand(GsodUseCaseWithPagining uasecase) {
		this.uasecase = uasecase;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// String startDate = request.getParameter("startDate").trim();
		// String endDate = request.getParameter("endDate").trim();
		// String country = request.getParameter("countrycode");
		// String state = request.getParameter("stateCode");

		// GsodCodeDateRequest dateRequest = new GsodCodeDateRequest();
		// dateRequest.setEndDate(endDate);
		// dateRequest.setStartDate(startDate);
		// dateRequest.setCode(country.trim().toLowerCase());
		// dateRequest.setStateCode(state.trim().toLowerCase());

		GsodParamReader reader = new GsodWebParamreader(request);
		int size = uasecase.getSummeryByCountryStateCodeAndDateRange(reader).size();
		int page = size % 50 == 0 ? size / 50 : (size / 50) + 1;

		String params = size + "," + page;

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(params);
	}

}
