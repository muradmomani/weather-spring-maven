package com.progressoft.jipfive.importcommands.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.progressoft.jipfive.command.Command;

public class LoginViewCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute("userName") != null) {
			request.getRequestDispatcher("/noaa/import/main/view").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/gsodImportViews/login.jsp").forward(request, response);
	}

}
