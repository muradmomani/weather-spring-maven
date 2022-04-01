package com.progressoft.jipfive.json.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

// TODO you can enhance this to be abstract
public class JsonHandler<T> {

	public void writeJson(T item, HttpServletResponse response) throws IOException {
		Gson stateAsJson = new Gson();
		String jsonElements = stateAsJson.toJson(item);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.write(jsonElements);
	}

}
