package com.progressoft.jipfive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class DBConnectionFactory implements AutoCloseable, Factory<Connection> {
	private String url;
	private String userName;
	private String passwd;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public DBConnectionFactory(String url, String userName, String passwd) {
		this.url = url;
		this.userName = userName;
		this.passwd = passwd;
	}

	public Connection create() {
		checkParametersForNullOrEmpty();
		return getConnection();
	}

	@Override
	public void close() throws Exception {

	}

	private Connection getConnection() {
		try {
			DriverManager.registerDriver(new Driver());
			return DriverManager.getConnection(url, userName, passwd);
		} catch (SQLException e) {
			throw new CantMakeConnectionException("Cant Make Connection In The Factory", e);
		}
	}

	private void checkParametersForNullOrEmpty() {
		if (isNullConfig()) {
			throw new BadDBConfigurationArigumentException("Bad DB Configuration Arigument ! Parameter is null");
		}
		if (isEmptyConfig()) {
			throw new BadDBConfigurationArigumentException("Bad DB Configuration Arigument ! parameter is empty");
		}
	}

	private boolean isEmptyConfig() {
		return url.isEmpty() || userName.isEmpty() || passwd.isEmpty();
	}

	private boolean isNullConfig() {
		return url == null || userName == null || passwd == null;
	}
}
