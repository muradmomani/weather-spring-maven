package com.progressoft.jipfive;

public class CantListFilesException extends RuntimeException {
	public CantListFilesException(String cant_list_dates_from_gsod) {
		super(cant_list_dates_from_gsod);
	}
}
