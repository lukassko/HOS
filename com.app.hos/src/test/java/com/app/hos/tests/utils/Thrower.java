package com.app.hos.tests.utils;

import com.app.hos.persistance.models.Connection;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.exceptions.HistoryConnectionException;
import com.app.hos.utils.exceptions.WebSocketJsonException;
import com.app.hos.utils.exceptions.NotExecutableCommandException;

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
