package com.app.hos.server.event;

import com.app.hos.server.connection.Connection;

public enum TcpEventType implements EventFactory {

	OPEN_CONNECTION((connection, throwable) -> new TcpOpenConnectionEvent(connection)),
	CLOSE_CONNECTION((connection, throwable) -> new TcpCloseConnectionEvent(connection)),
	EXCEPTION((connection, throwable) -> new TcpServerExceptionEvent(connection,throwable));

	private TcpEventType(EventFactory factory) {
		this.factory = factory;
	}
	
	private EventFactory factory;
	
	@Override
	public TcpEvent create(Connection connection, Throwable throwable) {
		return factory.create(connection,throwable);
	}
}
