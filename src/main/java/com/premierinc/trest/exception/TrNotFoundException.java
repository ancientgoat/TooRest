package com.premierinc.trest.exception;

/**
 *
 */
public class TrNotFoundException extends RuntimeException {
	public TrNotFoundException() {
		super();
	}

	public TrNotFoundException(String message) {
		super(message);
	}

	public TrNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
