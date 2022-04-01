package com.progressoft.jipfive;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.commandfactory.CommandFactory;

public class GsodProxy extends HttpServlet {
	// TODO you can have only one servlet with different mapping

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommandFactory stationCommandFactory;

	public GsodProxy(CommandFactory stationCommandFactory) {
		this.stationCommandFactory = stationCommandFactory;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getServerName());
		System.out.println("@ Gsod Proxy");
		// TODO add this to a filter so if you change or want to remove this, just play
		// with the filter configuration
		if (req.getServerName().equals("jobs.noaa.org")) {
			try {
				stationCommandFactory.getCommand(req.getPathInfo()).execute(req, resp);
			} catch (Exception e) {
				req.setAttribute("error", e.getMessage());
				req.getRequestDispatcher("/error/error.jsp").forward(req, resp);
			}
		}

	}

}
