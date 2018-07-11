package com.app.hos.server.event;

public enum TcpEventType implements EventFactory {

	OPEN_CONNECTION((connection, throwable) -> new TcpOpenConnectionEvent(connection)),
	CLOSE_CONNECTION((connection, throwable) -> new TcpCloseConnectionEvent(connection)),
	EXCEPTION((connection, throwable) -> new TcpServerExceptionEvent(connection,throwable));

	private TcpEventType(EventFactory factory) {
		this.factory = factory;
	}
	
	private EventFactory factory;
	
	@Override
	public TcpEvent create(Object cause, Throwable throwable) {
		return factory.create(cause,throwable);
	}
}
