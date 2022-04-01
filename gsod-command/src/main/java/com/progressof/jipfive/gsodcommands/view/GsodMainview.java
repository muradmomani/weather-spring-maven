package com.progressof.jipfive.gsodcommands.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.command.Command;


public class GsodMainview implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/gsodView/GsodMainView.jsp").forward(request, response);
	}
}
