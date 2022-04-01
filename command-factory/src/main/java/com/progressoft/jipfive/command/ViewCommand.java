package com.progressoft.jipfive.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewCommand implements Command {

	private String page;
	private ViewAction action;

	public ViewCommand(String page, ViewAction action) {
		this.page = page;
		this.action = action;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action.execute(request);
		request.getRequestDispatcher(page).forward(request, response);
	}

}
