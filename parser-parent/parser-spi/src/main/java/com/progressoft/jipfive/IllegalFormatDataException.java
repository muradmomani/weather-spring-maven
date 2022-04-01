package com.progressoft.jipfive;

public class IllegalFormatDataException extends RuntimeException {
	public IllegalFormatDataException() {
	}

	public IllegalFormatDataException(String message) {
		super(message);
	}

	public IllegalFormatDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalFormatDataException(Throwable cause) {
		super(cause);
	}

	public IllegalFormatDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
