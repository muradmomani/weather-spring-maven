package com.progressoft.jipfive.usecases.imp;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.progressoft.jipfive.Factory;
import com.progressoft.jipfive.GSODFileImporter;
import com.progressoft.jipfive.Gsod;
import com.progressoft.jipfive.GsodDAO;
import com.progressoft.jipfive.ImportReport;
import com.progressoft.jipfive.Parser;
import com.progressoft.jipfive.paramreaders.spi.ImportParamReader;
import com.progressoft.jipfive.usecases.spi.ImportUseCase;

public class DefaultImportUseCase implements ImportUseCase {

	private Path dirName = Paths.get("/home/u687/noaa");

	private GsodDAO fileGsodDAO;
	private Factory<Connection> connectionFactory;
	private Path gsodDirPath;
	private Parser parser;

	private Scanner in = new Scanner(System.in);
	private Executor executor;
	private Queue<String> filesInTheThread;
	private List<ImportReport> statues;

	public DefaultImportUseCase(Parser<Gsod, String> parser, GsodDAO fileGsodDAO,
			Factory<Connection> connectionFactory) {
		this.executor = Executors.newFixedThreadPool(2);
		this.fileGsodDAO = fileGsodDAO;
		this.connectionFactory = connectionFactory;
		this.setFilesInTheThread(new LinkedList<>());
		this.statues = new ArrayList<>();
		this.parser = parser;

	}

	@Override
	public List<String> getAvilableFiles() {
		return (List<String>) fileGsodDAO.getAvilableDates();
	}

	@Override
	public void importGsodFile(ImportParamReader reader) {
		String fileName = reader.getFileName();
		try {
			notCorrectFileName(fileName);
		} catch (Exception e) {
			throw new InvalidInputExcetion(e.getMessage());
		}
		if (isInTheQueu(fileName)) {
			throw new FileAlreadyInTheThreadException("The file you specified is already in the Thread");
		}
		GSODFileImporter importGsodFile = prepareImportGsodFile(fileName);
		try {
			executor.execute(importGsodFile);
		} catch (Exception e) {
			throw new CantWriteDataToDataBaseException("in Import Method" + e.getMessage());
		}

	}

	@Override
	public List<ImportReport> gsodImportJobStatus() {
		return statues;
	}

	private void notCorrectFileName(String fileName) {
		fileName.replace(".gsod", "");
		if (fileName.length() != 8) {
			throw new RuntimeException("File Name Is not Correct !");
		}
		Collection<String> dates = fileGsodDAO.getAvilableDates();
		Optional<String> first = dates.stream().filter((date) -> date.equals(fileName)).findFirst();
		if (!first.isPresent()) {
			throw new RuntimeException("File Not Exist!");
		}
	}

	private boolean isInTheQueu(String fileName) {
		return getFilesInTheThread().contains(fileName);
	}

	private GSODFileImporter prepareImportGsodFile(String fileName) {
		Path filePath = Paths.get(dirName.toString() + File.separator + "gsod" + File.separator + fileName + ".gsod");
		getFilesInTheThread().add(fileName);
		System.out.println("files in the thread : " + getFilesInTheThread());
		GSODFileImporter importGsodFile = new GSODFileImporter(parser, connectionFactory, filePath,
				getFilesInTheThread());
		statues.add(importGsodFile.getImportReport());
		return importGsodFile;
	}

	@Override
	public Queue<String> getFilesInTheThread() {
		return filesInTheThread;
	}

	public void setFilesInTheThread(Queue<String> filesInTheThread) {
		this.filesInTheThread = filesInTheThread;
	}
}
