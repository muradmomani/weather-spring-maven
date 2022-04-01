package com.progressoft.jipfive.param.reader.web;

import javax.servlet.http.HttpServletRequest;

import com.progressoft.jipfive.paramreaders.spi.StationParamReader;

public class StationWebParamReader implements StationParamReader {

	private HttpServletRequest request;

	public StationWebParamReader(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getStationName() {
		return null;
	}

	@Override
	public String getCountryCode() {
		return request.getParameter("country");
	}

	@Override
	public String getStateCode() {
		return request.getParameter("state");
	}

	@Override
	public Integer getWban() {
		return Integer.valueOf(request.getParameter("wban").trim());
	}

	@Override
	public Integer getUsaf() {
		return Integer.valueOf(request.getParameter("usaf").trim());
		}

	@Override
	public Integer getUsaf2() {
		return null;
	}

	@Override
	public Double getLat() {
		return Double.valueOf(request.getParameter("lat"));
	}

	@Override
	public Double getLang() {
		return Double.valueOf(request.getParameter("long"));
	}

}
