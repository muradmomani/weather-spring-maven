
package com.progressoft.jipfive;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.commandfactory.CommandFactory;

//TODO it is recommended to not put code into war projects
public class StationsProxy extends HttpServlet {
	private static final long serialVersionUID = 10L;

	private CommandFactory factory;

	public StationsProxy(CommandFactory factory) {
		this.factory = factory;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getServerName());
		System.out.println("@ Stations Proxy");
		if (request.getServerName().equals("jobs.noaa.org")) {
			try {
				System.out.println("Path info : " + request.getPathInfo());
				factory.getCommand(request.getPathInfo()).execute(request, response);
			} catch (Exception e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/error/error.jsp").forward(request, response);
			}
		}

	}

}
