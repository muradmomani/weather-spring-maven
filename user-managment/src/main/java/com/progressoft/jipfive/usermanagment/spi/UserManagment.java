package com.progressoft.jipfive.usermanagment.spi;

public interface UserManagment {

	String getPasswd(String userName);

	void updateUser(String uerName, String passwd);

	boolean isUserExist(String userName, String passwd);
}
