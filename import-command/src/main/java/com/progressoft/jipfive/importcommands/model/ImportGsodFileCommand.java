package com.progressoft.jipfive.importcommands.model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.param.reader.web.ImportWebParamReader;
import com.progressoft.jipfive.usecases.spi.ImportUseCase;

public class ImportGsodFileCommand implements Command {

	private ImportUseCase usecase;

	public ImportGsodFileCommand(ImportUseCase usecase) {
		this.usecase = usecase;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ImportWebParamReader reader = new ImportWebParamReader(request);
		usecase.importGsodFile(reader);

	}

}
