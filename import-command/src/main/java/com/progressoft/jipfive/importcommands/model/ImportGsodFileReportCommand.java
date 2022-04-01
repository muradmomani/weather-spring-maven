package com.progressoft.jipfive.importcommands.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.progressoft.jipfive.ImportReport;
import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.usecases.spi.ImportUseCase;

public class ImportGsodFileReportCommand implements Command {

	private ImportUseCase useCase;

	public ImportGsodFileReportCommand(ImportUseCase useCase) {
		this.useCase = useCase;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ImportReport> gsodImportJobStatus = useCase.gsodImportJobStatus();
		Gson convertor = new Gson();
		String json = convertor.toJson(gsodImportJobStatus);
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");
		writer.write(json);
		writer.flush();
	}

}
