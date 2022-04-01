package com.progressoft.jipfive;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileDataStore<T> implements DataStore<T> {

	private String dirName;
	private Parser<T, String> parser;

	public FileDataStore(File dirNAme, Parser<T, String> parser) {
		this.parser = parser;
		this.dirName = dirNAme.toString();
	}

	@Override
	public String getDirName() {
		return dirName;
	}

	@Override
	public Collection<T> readObjects(String file) {
		// Collection<String> lines = null;
		List<T> list = new ArrayList<>();

		try {
			// lines = Files.readAllLines(Paths.get(dirName, file));
			Files.lines(Paths.get(dirName, file)).skip(1).forEach(l -> list.add(parser.parser(l)));
			System.out.println(Paths.get(dirName, file));
		} catch (IOException e) {
			System.out.println("cant read from file ! at new data store class");
		}
		return list;
	}
}
