package com.app.hos.tests.utils;

import com.app.hos.persistance.models.Connection;
import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.exceptions.WebSocketJsonException;
import com.app.hos.share.command.type.CommandType;

public class Thrower {
	
	public void throwNotExeccutableCommandException () {
		throw new NotExecutableCommandException(CommandType.UNKNOWN_COMMAND);
	}
	
	public void throwHistoryConnectionException (Connection connection) {
		throw new HistoryConnectionException(connection);
	}

	public void throwJsonException () {
		throw new WebSocketJsonException(new Throwable());
	}
}
