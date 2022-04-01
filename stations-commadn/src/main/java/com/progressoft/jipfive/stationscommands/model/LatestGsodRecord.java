package com.progressoft.jipfive.stationscommands.model;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.Gsod;
import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.param.reader.web.GsodWebParamreader;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;

public class LatestGsodRecord implements Command {

	private GsodUseCase usecase;

	public LatestGsodRecord(GsodUseCase usecase) {
		this.usecase = usecase;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		GsodWebParamreader reader = new GsodWebParamreader(request);

		List<Gsod> latestGsodRecord = usecase.latestGsodRecord(reader);
		request.setAttribute("date", null);
		request.setAttribute("summery", latestGsodRecord);
		request.getRequestDispatcher("/WEB-INF/latestGsodRecords.jsp").forward(request, response);

	}
}
