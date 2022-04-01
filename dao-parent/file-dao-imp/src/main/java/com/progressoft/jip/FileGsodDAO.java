package com.progressoft.jip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.progressoft.jipfive.CantListFilesException;
import com.progressoft.jipfive.DataStore;
import com.progressoft.jipfive.Gsod;
import com.progressoft.jipfive.GsodDAO;
import com.progressoft.jipfive.ID;

public class FileGsodDAO implements GsodDAO {
	private String GSOD_DIR = "/gsod";
	private DataStore<Gsod> dataStore;

	public FileGsodDAO(DataStore dataStore/* , Parser<Gsod, String> parser */) {
		// this.parser = parser;
		this.dataStore = dataStore;
	}

	public Collection<String> getAvilableDates() {

		Path dir = Paths.get(dataStore.getDirName());
		Stream<Path> list = null;
		try {
			list = Files.list(dir);
		} catch (IOException e) {
			throw new CantListFilesException("cant list dates from gsod");
		}

		Collection<String> fileNames = pareseFileToString(list.collect(Collectors.toList()));
		return fileNames;
	}

	public Collection<Gsod> getSummeryByIDAndDate(ID id, String fileName) {
		// Collection<Gsod> gsods = new ArrayList<>();
		// Collection<String> lines = dataStore.readObjects("/" + fileName + ".gsod");
		// int counter = 0;
		// for (String str : lines) {
		// if (counter == 0) {
		// counter++;
		// continue;
		// }
		// gsods.add(parser.parser(str));
		// }

		Collection<Gsod> gsods = dataStore.readObjects(/* GSOD_DIR + */ "/" + fileName + ".gsod");
		List<Gsod> collect = gsods.stream().filter((gsod) -> gsod.getId().getUsaf() == id.getUsaf())
				.filter((gsod) -> gsod.getId().getWban() == id.getWban()).collect(Collectors.toList());

		return collect;
	}

	private Collection<String> pareseFileToString(Collection<Path> files) {
		Collection<String> fileNames = files.stream().map((file) -> {
			String[] str = file.toString().split("/");
			return str[str.length - 1];
		}).map((string) -> string.replace(".gsod", "")).collect(Collectors.toList());
		return fileNames;
	}
}
