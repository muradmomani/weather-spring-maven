package com.progressoft.jipfive;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StationsImporter implements StationsImporterSPI {
	private Factory<Connection> connectionFactory;
	private Parser<Station, String> toStationParser;

	public StationsImporter(Factory<Connection> connectionFactory, Parser<Station, String> parser) {
		this.connectionFactory = connectionFactory;
		this.toStationParser = parser;
	}

	@Override
	public void writeStationsToDataBase() {
		Path dirName = Paths.get("/home/u687/noaa");
		Path stationsFile = Paths.get(dirName.toString() + File.separator + "Stations.txt");
		if (Files.notExists(stationsFile))
			return;
		try (Connection connection = connectionFactory.create()) {
			Collection<Station> stations;
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);

			List<String> lines = Files.readAllLines(stationsFile);
			lines.remove(0);
			stations = lines.stream().map(toStationParser::parser).collect(Collectors.toList());

			insertStationsToDB(connection, stations);

			moveToArchive(dirName, stationsFile);
		} catch (IOException | NumberFormatException e) {
			System.err.println("can't read from the stations file to data base!");
			// throw new CantReadFromStationsFileToDataBaseException( "can't read from the
			// stations file to data base!", e );
		} catch (SQLException e) {
			System.err.println("Can't write stations to the dataBase!");

			// throw new StationsImportException( "Can't write stations to the dataBase!" );
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	private void insertStationsToDB(Connection connection, Collection<Station> stations) {
		String insertStatment = "insert into STATIONS values(?,?,?,?,?,?,?,?,?,?,?)"
				+ " ON DUPLICATE KEY UPDATE USFA=?,WBAN=?," + "STATION_NAME=?,CTRY=?,ST=?,ICAO=?,LAT=?,LON=?,"
				+ "ELEV=?,BGIN=?,ND=?";
		try {
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatment)) {
				writeStationToDataBase(stations, preparedStatement);
				preparedStatement.executeBatch();
				connection.commit();
			} catch (BatchUpdateException e) {
				connection.rollback();
				int[] updates = e.getUpdateCounts();
				for (int i = 0; i < updates.length; i++)
					if (updates[i] == Statement.EXECUTE_FAILED)
						throw new StationsImportException("update is not updated in row #" + i, e);
			}
		} catch (SQLException e) {
			throw new StationsImportException("Can't write stations to the dataBase!", e);
		}

	}

	@SuppressWarnings("unused")
	private void parseToStations(Collection<Station> stations, Collection<String> lines) {
		int counter = 0;
		for (String line : lines) {
			if (counter == 0) {
				counter++;
				continue;
			}
			stations.add(toStationParser.parser(line));
		}
	}

	private void moveToArchive(Path dirName, Path stationsFile) throws IOException {
		Path archive = Paths.get(dirName.toString() + File.separator + "archive");
		if (!Files.exists(archive)) {
			Files.createDirectory(archive);
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		archive = Paths.get(archive.toString() + File.separator + "Stations." + localDateTime.toLocalTime());
		Files.move(stationsFile, archive);
	}

	private void writeStationToDataBase(Collection<Station> stations, PreparedStatement preparedStatement)
			throws SQLException {
		for (Station station1 : stations) {
			/*
			 * we made get instances avilabe in the station class then we return array of
			 * stations fileds then for loop and pass them to method whivh will set the
			 * object
			 */
			// Object[] objects = station1.getInstanceVariables();
			// for (int i = 0; i < objects.length; i++)
			// setParam( preparedStatement, i + 1, objects[i] );
			//// setParam( preparedStatement, 2, station1.getId().getWban() );
			preparedStatement.setInt(1, station1.getId().getUsaf());
			preparedStatement.setInt(2, station1.getId().getWban());
			preparedStatement.setString(3, station1.getStationName());
			preparedStatement.setString(4, station1.getCiry());
			preparedStatement.setString(5, station1.getSt());
			preparedStatement.setString(6, station1.getCall());

			// preparedStatement.setDouble(7, station1.getLat());
			// preparedStatement.setDouble(8, station1.getLang());
			if (station1.getLat() == null)
				preparedStatement.setNull(7, Types.DOUBLE);
			else
				preparedStatement.setDouble(7, station1.getLat() == null ? null : station1.getLat());

			if (station1.getLang() == null)
				preparedStatement.setNull(8, Types.DOUBLE);
			else
				preparedStatement.setDouble(8, station1.getLang() == null ? null : station1.getLang());

			preparedStatement.setDouble(9, station1.getElev());
			preparedStatement.setString(10, station1.getBegin());
			preparedStatement.setString(11, station1.getEnd());

			preparedStatement.setInt(12, station1.getId().getUsaf());
			preparedStatement.setInt(13, station1.getId().getWban());
			preparedStatement.setString(14, station1.getStationName());
			preparedStatement.setString(15, station1.getCiry());
			preparedStatement.setString(16, station1.getSt());
			preparedStatement.setString(17, station1.getCall());

			// preparedStatement.setDouble(18, station1.getLat());
			// preparedStatement.setDouble(19, station1.getLang());
			if (station1.getLat() == null)
				preparedStatement.setNull(18, Types.DOUBLE);
			else
				preparedStatement.setDouble(18, station1.getLat() == null ? null : station1.getLat());

			if (station1.getLang() == null)
				preparedStatement.setNull(19, Types.DOUBLE);
			else
				preparedStatement.setDouble(19, station1.getLang() == null ? null : station1.getLang());

			preparedStatement.setDouble(20, station1.getElev());
			preparedStatement.setString(21, station1.getBegin());
			preparedStatement.setString(22, station1.getEnd());

			preparedStatement.addBatch();
			// System.out.println(station1);
			// preparedStatement.execute();

		}
	}

	@SuppressWarnings("unused")
	private void setParam(PreparedStatement preparedStatement, int i, Object o) throws SQLException {
		preparedStatement.setObject(i, o);
		preparedStatement.setObject(i + 11, o);

	}

}
