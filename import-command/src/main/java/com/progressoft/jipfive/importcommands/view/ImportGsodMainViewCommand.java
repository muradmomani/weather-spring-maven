package com.progressoft.jipfive.importcommands.view;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;
import com.progressoft.jipfive.usecases.spi.ImportUseCase;

public class ImportGsodMainViewCommand implements Command {

	private ImportUseCase usecase;

	public ImportGsodMainViewCommand(ImportUseCase usecase) {
		this.usecase = usecase;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> dates = usecase.getAvilableFiles().stream().sorted().collect(Collectors.toList());
		request.setAttribute("dates", dates);
		request.getRequestDispatcher("/gsodImportViews/ImporterMainView.jsp").forward(request, response);
		return;
	}

}
