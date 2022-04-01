package com.progressoft.jipfive;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseStringParser implements Parser<User, ResultSet> {

	@Override
	public User parser(ResultSet line) throws IllegalFormatDataException {
		User user = new User();
		try {
			user.setUserName(line.getString("PASSWORD"));
			user.setPasswd(line.getString("USERNAME"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
