package com.progressoft.jipfive;

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

import com.progressoft.jipfive.importGsod.requests.ImportGsodFileRequest;
import com.progressoft.jipfive.recovery.thread.RecoveryParent;

public class ImportAndStatusOperations implements ImportAndStatusOperation {
	private Path dirName = Paths.get("/home/u687/noaa");

	private GsodDAO fileGsodDAO;
	private Factory<Connection> connectionFactory;
	private Path gsodDirPath;
	private Parser<Gsod, String> parser;

	private Scanner in = new Scanner(System.in);
	private Executor executor;
	private Queue<String> filesInTheThread;
	private List<ImportReport> statues;

	public ImportAndStatusOperations(Parser<Gsod, String> parser, GsodDAO fileGsodDAO,
			Factory<Connection> connectionFactory, RecoveryParent recoverThread) {
		this.executor = Executors.newFixedThreadPool(2);
		this.fileGsodDAO = fileGsodDAO;
		this.connectionFactory = connectionFactory;
		this.filesInTheThread = new LinkedList<>();
		this.statues = new ArrayList<>();
		this.parser = parser;

		recoverThread.setListOfFile(filesInTheThread);

	}

	@Override
	public List<String> getAvilableFiles() {
		return (List<String>) fileGsodDAO.getAvilableDates();
	}

	@Override
	public void importGsodFile(ImportGsodFileRequest request) {
		String fileName = request.getFileName();
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

	// option 6
	@Override
	public List<ImportReport> gsodImportJobStatus() {
		return statues;
	}

	private String getFileName() {
		System.out.println("Available Dates:");
		System.out.println(fileGsodDAO.getAvilableDates());
		System.out.println("insert the File To be imported to DataBase: ");
		return in.nextLine().trim();
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
		return filesInTheThread.contains(fileName);
	}

	private GSODFileImporter prepareImportGsodFile(String fileName) {
		Path filePath = Paths.get(dirName.toString() + File.separator + "gsod" + File.separator + fileName + ".gsod");
		filesInTheThread.add(fileName);
		System.out.println("files in the thread : " + filesInTheThread);
		GSODFileImporter importGsodFile = new GSODFileImporter(parser, connectionFactory, filePath, filesInTheThread);
		statues.add(importGsodFile.getImportReport());
		return importGsodFile;
	}

}
