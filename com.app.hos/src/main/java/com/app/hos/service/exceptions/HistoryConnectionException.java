package com.app.hos.service.exceptions;

import com.app.hos.persistance.models.connection.Connection;

public class HistoryConnectionException extends HOSException {

	private static final long serialVersionUID = 1L;
	
	public HistoryConnectionException(Connection connection) {
		super("Current connection is not ended: " + connection.getConnectionId());
	}

}
