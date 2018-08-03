package com.app.hos.common;

import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.exceptions.WebSocketJsonException;

public class Thrower {
	
	public void throwNotExeccutableCommandException () {
		throw new NotExecutableCommandException(CommandType.UNKNOWN);
	}
	
	public void throwHistoryConnectionException (Connection connection) {
		throw new HistoryConnectionException(connection);
	}

	public void throwJsonException () {
		throw new WebSocketJsonException(new Throwable());
	}
}
