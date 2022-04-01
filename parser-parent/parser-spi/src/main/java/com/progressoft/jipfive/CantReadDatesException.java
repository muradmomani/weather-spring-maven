package com.progressoft.jipfive;

public class CantReadDatesException extends IllegalFormatDataException {
	public CantReadDatesException(String message) {
		super(message);
	}

	public CantReadDatesException(String string, Exception e) {
		super(string, e);
	}
}
