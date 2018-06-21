package com.app.hos.service.exceptions;

public class SerializationException extends HOSException {

	private static final long serialVersionUID = 1L;
	
	public SerializationException(String message, Throwable throwable) {
		super(message,throwable);
	}

}
