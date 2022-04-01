package com.progressoft.jipfive;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import com.progressoft.jipfive.ImportReport.Status;

public class GSODFileImporter implements Runnable {

	private Path dirName, filePath;
	private String fileName;
	private ImportReport importReport;
	private Factory<Connection> connectionFactory;
	private Parser<Gsod, String> stringToGsodParser;

	private Queue<String> queue;

	public GSODFileImporter(Parser<Gsod, String> parser, Factory<Connection> connectionFactory, Path filePath,
			Queue<String> queue) {
		this.dirName = filePath.getParent().getParent();
		this.fileName = filePath.getFileName().toString().replaceAll(".gsod", "");
		this.filePath = filePath;
		this.importReport = new ImportReport();
		this.stringToGsodParser = parser;

		importReport.setJobName(fileName + " Import");
		importReport.setStatus(Status.WATING);
		importReport.setQueuingTime(LocalDateTime.now());
		this.connectionFactory = connectionFactory;
		this.queue = queue;
	}

	public ImportReport getImportReport() {
		return importReport;
	}

	public void run() {

		importReport.setStartTime(LocalDateTime.now());
		importReport.setStatus(Status.RUNNING);
		try {
			Collection<Gsod> gsods = readGsodFromFile(filePath);
			writeGsodToDataBase(gsods, fileName);
			moveGSODToArchive(dirName, filePath);
			setStatusEndTimeMessageAndDuration("Done", Status.COMPLETED);
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			setStatusEndTimeMessageAndDuration(errorMessage, Status.FAILD);
		} finally {
			queue.poll();
		}
	}

	private void setStatusEndTimeMessageAndDuration(String message, Status status) {
		importReport.setStatus(status);
		importReport.setMessage(message);
		importReport.setEndTime(LocalDateTime.now());
		importReport.setTotalTime();
	}

	private Collection<Gsod> readGsodFromFile(Path filePath) throws IOException {
		Collection<String> lines = Files.readAllLines(filePath);
		Collection<Gsod> gsods = new ArrayList<>();
		int counter = 0;
		for (String line : lines) {
			if (counter == 0) {
				counter++;
				continue;
			}
			try {
				gsods.add(stringToGsodParser.parser(line));
				counter++;
			} catch (CantReadDatesException e) {
				throw new IllegalFormatDataException(e.getMessage() + "at Row #" + counter + " in the File", e);
			}
		}
		return gsods;
	}

	private void writeGsodToDataBase(Collection<Gsod> gsods, String fileName) throws SQLException {
		int counter = 1;
		Connection connection = connectionFactory.create();
		connection.setAutoCommit(false);
		String insert = "insert into GSOD values(?,?,?,?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
			for (Gsod gsod1 : gsods) {
				executeUpdate(fileName, preparedStatement, gsod1);
				counter++;
			}
			connection.commit();
			setStatusEndTimeMessageAndDuration(counter + " Rows Addedd successfuly", Status.COMPLETED);
		} catch (SQLException e) {
			connection.rollback();
			throw new SQLException("Record #" + counter + " " + e.getMessage());
		}
	}

	private void executeUpdate(String fileName, PreparedStatement preparedStatement, Gsod gsod1) throws SQLException {
		int usaf = gsod1.getId().getUsaf();
		int wban = gsod1.getId().getWban();
		int temp = gsod1.getTemp();
		preparedStatement.setInt(1, usaf);
		preparedStatement.setInt(2, wban);
		preparedStatement.setInt(3, temp);
		preparedStatement.setString(4, fileName);
		preparedStatement.executeUpdate();
	}

	private void moveGSODToArchive(Path dirName, Path gsodName) {
		try {
			Path archive = Paths.get(dirName.toString() + File.separator + "archive");
			if (!Files.exists(archive))
				Files.createDirectory(archive);
			LocalDateTime localDateTime = LocalDateTime.now();
			archive = Paths.get(archive.toString() + File.separator + gsodName.getFileName().toString()
					+ localDateTime.toLocalTime());
			Files.move(gsodName, archive);
		} catch (IOException ex) {
			throw new CantWriteDataToFileException("Cant write data to file", ex);
		}
	}
}
