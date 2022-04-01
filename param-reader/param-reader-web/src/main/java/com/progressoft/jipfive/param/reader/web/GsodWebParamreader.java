package com.progressoft.jipfive.param.reader.web;

import javax.servlet.http.HttpServletRequest;

import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;

public class GsodWebParamreader implements GsodParamReader {

	private HttpServletRequest request;

	public GsodWebParamreader(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public Integer getUsaf() {
		return Integer.valueOf(request.getParameter("usaf").trim());
	}

	@Override
	public Integer getWban() {
		return Integer.valueOf(request.getParameter("wban").trim());
	}

	@Override
	public String getDate() {
		return request.getParameter("date").trim();
	}

	@Override
	public String getCountryCode() {
		return request.getParameter("countrycode").trim();
	}

	@Override
	public String getStationName() {
		return null;
	}

	@Override
	public String getStartDate() {
		return request.getParameter("startDate").trim();
	}

	@Override
	public String getEndDate() {
		return request.getParameter("endDate").trim();
	}

	@Override
	public String getStateCode() {
		return request.getParameter("stateCode").trim();
	}

	@Override
	public Integer getPageNumber() {
		return null;
	}

	@Override
	public Integer getPage() {
		return Integer.valueOf(request.getParameter("page"));
	}

}
