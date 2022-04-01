package com.progressoft.jipfive;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;

public class OperationsFactory {

	private static StationOperationsInterface stationOperations;
	private GSODOperation gsodOPerations;
	private ImportAndStatusOperation importOperation;

	private static Factory<Connection> connectionFactory = preparingConnectionFactory();

	public static StationOperationsInterface getStationsFactory() {

		if (getStationOperations() == null) {
			Parser<Station, ResultSet> parser = new DataBaseStationParser();
			DBDataStore<Station> dataStore = new DBDataStore(parser, getConnectionFactory());
			StationDAO dao = new StationsDataBaseDAO(dataStore);
			setStationOperations(new StationOperations(dao));
		}
		return getStationOperations();
	}

	private static Factory preparingConnectionFactory() {
		String url = "jdbc:mysql://localhost:3306/weather?useSSL=false";
		String userName = "root";
		String passwd = "root";
		return new DBConnectionFactory(url, userName, passwd);
	}

	public static StationOperationsInterface getStationOperations() {
		return stationOperations;
	}

	public static void setStationOperations(StationOperationsInterface stationOperations) {
		OperationsFactory.stationOperations = stationOperations;
	}

	public GSODOperation getGsodOPerations() {
		return gsodOPerations;
	}

	public void setGsodOPerations(GSODOperation gsodOPerations) {
		this.gsodOPerations = gsodOPerations;
	}

	public ImportAndStatusOperation getImportOperation() {
		return importOperation;
	}

	public void setImportOperation(ImportAndStatusOperation importOperation) {
		this.importOperation = importOperation;
	}

	public static Factory<Connection> getConnectionFactory() {
		return connectionFactory;
	}

	public static void setConnectionFactory(Factory<Connection> connectionFactory) {
		OperationsFactory.connectionFactory = connectionFactory;
	}
}
