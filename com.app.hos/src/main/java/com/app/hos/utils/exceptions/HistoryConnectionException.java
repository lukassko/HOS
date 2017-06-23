package com.app.hos.utils.exceptions;

public class HistoryConnectionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public HistoryConnectionException() {
		super("Current connection is not ended");
	}

}
