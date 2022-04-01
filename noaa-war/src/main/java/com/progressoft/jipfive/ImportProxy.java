//package com.progressoft.jipfive;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.progressoft.jipfive.commandfactory.CommandFactory;
//
//public class ImportProxy extends HttpServlet {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	CommandFactory importCommandFactory;
//
//	public ImportProxy(CommandFactory importCommandFactory) {
//		this.importCommandFactory = importCommandFactory;
//	}
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doPost(req, resp);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println(req.getServerName());
//		System.out.println("@ Import Proxy");
//		if (req.getServerName().equals("jobs.noaa.org")) {
//			try {
//				importCommandFactory.getCommand(req.getPathInfo()).execute(req, resp);
//			} catch (Exception e) {
//				req.setAttribute("error", e.getMessage());
//				req.getRequestDispatcher("/error/error.jsp").forward(req, resp);
//			}
//		}
//
//	}
//
//}
