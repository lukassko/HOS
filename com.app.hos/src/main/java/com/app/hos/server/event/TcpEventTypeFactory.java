package com.app.hos.server.event;

import com.app.hos.server.event.source.TcpEventSource;

public enum TcpEventTypeFactory implements EventFactory {

	OPEN_CONNECTION(TcpOpenConnectionEvent::new),
	CLOSE_CONNECTION(TcpCloseConnectionEvent::new),
	SERVER_EXCEPTION(TcpServerExceptionEvent::new),
	CONNECTION_EXCEPTION(TcpConnectionExceptionEvent::new);
	
	private TcpEventTypeFactory(EventFactory factory) {
		this.factory = factory;
	}
	
	private EventFactory factory;
	
	@Override
	public TcpEvent create(TcpEventSource source) {
		return factory.create(source);
	}
}
