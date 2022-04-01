package com.progressoft.jipfive.param.reader.web;

import javax.servlet.http.HttpServletRequest;

import com.progressoft.jipfive.paramreaders.spi.ImportParamReader;

public class ImportWebParamReader implements ImportParamReader {
	private HttpServletRequest request;

	public ImportWebParamReader(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getFileName() {
		return request.getParameter("fileName");
	}

}
