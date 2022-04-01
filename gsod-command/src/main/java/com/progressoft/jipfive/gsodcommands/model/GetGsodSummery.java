package com.progressoft.jipfive.gsodcommands.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;
import com.progressoft.jipfive.usecases.spi.GsodUseCaseWithPagining;

public class GetGsodSummery {
	private GsodUseCaseWithPagining uasecase;

	public GetGsodSummery(GsodUseCaseWithPagining useCaseWithPagining) {
		this.uasecase = useCaseWithPagining;
	}

	public List<GsodSummery> getSummeryList(HttpServletRequest request, HttpServletResponse response) {
		String startDate = request.getParameter("startDate").trim();
		String endDate = request.getParameter("endDate").trim();
		String country = request.getParameter("countrycode");
		String state = request.getParameter("stateCode");

		Integer Page = Integer.valueOf(request.getParameter("page"));

		// GsodCodeDateRequest dateRequest = new GsodCodeDateRequest();
		// dateRequest.setEndDate(endDate);
		// dateRequest.setStartDate(startDate);
		// dateRequest.setCode(country.trim().toLowerCase());
		// dateRequest.setStateCode(state.trim().toLowerCase());
		GsodParamReader reader = new GsodParamReader() {

			@Override
			public Integer getWban() {
				return null;
			}

			@Override
			public Integer getUsaf() {
				return null;
			}

			@Override
			public String getStationName() {
				return null;
			}

			@Override
			public String getStateCode() {
				return state;
			}

			@Override
			public String getStartDate() {
				return startDate;
			}

			@Override
			public Integer getPageNumber() {
				return null;
			}

			@Override
			public Integer getPage() {
				return Page;
			}

			@Override
			public String getEndDate() {
				return endDate;
			}

			@Override
			public String getDate() {
				return null;
			}

			@Override
			public String getCountryCode() {
				return country;
			}
		};
		List<GsodSummery> summeryByDateRange = uasecase.getSummeryByCtryDateAndPaging(reader);
		return summeryByDateRange;
	}
}
