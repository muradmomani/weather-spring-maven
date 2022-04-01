package com.progressoft.jipfive.usermanagment.imp;

import java.util.Collection;

import com.progressoft.jipfive.DBQueryExecutor;
import com.progressoft.jipfive.User;
import com.progressoft.jipfive.usermanagment.spi.UserManagment;

public class UserManagemntImp implements UserManagment {
	private DBQueryExecutor<User> dataStore;

	public UserManagemntImp(DBQueryExecutor<User> dataStore) {
		this.dataStore = dataStore;
	}

	@Override
	public void updateUser(String uerName, String passwd) {
		String query = "UPDATE users SET  PASSWORD =? WHERE USERNAME= ? ";
		dataStore.executeQuery(query, passwd, uerName);
	}

	@Override
	public boolean isUserExist(String userName, String passwd) {
		String query = "select * from users where USERNAME =? AND PASSWORD =?";
		Collection<User> executeQuery = dataStore.executeQuery(query, userName, passwd);

		return executeQuery != null && executeQuery.size()>0;
	}

	@Override
	public String getPasswd(String userName) {
		return null;
	}

}
