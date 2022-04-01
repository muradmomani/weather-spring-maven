package com.progressoft.jipfive.gsodcommands.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.command.Command;

public class DownloadCSVCommand implements Command {

	private GetGsodSummery summery;
	private Map<String, GetValue> mapper = new HashMap<>();

	{
		mapper.put("usaf", gsodSummery -> gsodSummery.getId().getUsaf());
		mapper.put("wban", gsodSummery -> gsodSummery.getId().getWban());
		mapper.put("state", gsodSummery -> gsodSummery.getState());
		mapper.put("temp", gsodSummery -> gsodSummery.getTemp());
		mapper.put("country", gsodSummery -> gsodSummery.getCountry());
	}

	public DownloadCSVCommand(GetGsodSummery summery) {
		this.summery = summery;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<GsodSummery> summeryList = summery.getSummeryList(request, response);

		Enumeration<String> parameterNames = request.getParameterNames();
		List<String> columns = new ArrayList<>();

		prepareColumnsList(request, parameterNames, columns);


		// Path tempFile = prepareTempFile(tempString);
		//String fileName = "file.csv";// tempFile.getFileName().toString();
		//String mimeType = request.getServletContext().getMimeType(fileName.toString());

		response.setContentType("text/csv");
		// response.setContentLengthLong(Files.size(tempFile));
		response.setHeader("Content-Disposition", "attachment; filename=gsod-summary.csv");
		// Files.copy(tempFile, outputStream);
		PrintWriter outputStream = response.getWriter();
		// StringBuilder tempString = new StringBuilder("");

		columns.stream().forEach(c -> outputStream.print(c + ","));
		outputStream.print("\n");
		summeryList.stream().forEach(summery -> {
			columns.stream()
					.forEach(column -> outputStream.print(mapper.get(column).getObject(summery).toString() + ","));
			outputStream.print("\n");
		});

		outputStream.flush();

	}

	@SuppressWarnings("unused")
	private Path prepareTempFile(StringBuilder tempString) throws IOException, FileNotFoundException {
		Path tempFile = Files.createTempFile("temp2", ".txt");
		FileOutputStream out = new FileOutputStream(new File(tempFile.toUri()));
		out.write(tempString.toString().getBytes());
		out.close();//TODO
		return tempFile;
	}

	private void prepareColumnsList(HttpServletRequest request, Enumeration<String> parameterNames,
			List<String> columns) {
		while (parameterNames.hasMoreElements()) {
			String param = parameterNames.nextElement();
			String parameter = request.getParameter(param);
			if (param.contains("column")) {
				columns.add(parameter);
			}
		}
	}

	@FunctionalInterface
	interface GetValue {
		Object getObject(GsodSummery gsodSummery);
	}

}
// StringBuilder tempString = new StringBuilder("");
// columns.stream().forEach(c -> tempString.append(c + ","));
// tempString.append("\n");
// summeryList.stream().forEach(summery -> {
// columns.stream()
// .forEach(column ->
// tempString.append(mapper.get(column).getObject(summery).toString() + ","));
// tempString.append("\n");
// });