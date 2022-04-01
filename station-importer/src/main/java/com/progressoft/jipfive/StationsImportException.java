package com.progressoft.jipfive;


@SuppressWarnings("serial")
public class StationsImportException extends RuntimeException {
	public StationsImportException(String message) {
		super(message);
	}

	public StationsImportException(String message, Exception cause) {
		super(message, cause);
	}
}
