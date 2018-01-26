package com.app.hos.utils.exceptions;

import com.app.hos.persistance.models.Connection;

public class HistoryConnectionException extends HOSException {

	private static final long serialVersionUID = 1L;
	
	public HistoryConnectionException(Connection connection) {
		super("Current connection is not ended: " + connection.getConnectionId());
	}

}
