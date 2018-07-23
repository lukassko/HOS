package com.app.hos.server.event;

import com.app.hos.server.connection.SocketInfo;
import com.app.hos.server.event.source.TcpConnectionCorrelationFailedEventSource;
import com.app.hos.server.event.source.TcpConnectionEventSource;
import com.app.hos.server.event.source.TcpServerEventSource;
import com.app.hos.server.factory.TcpServerListener;

public enum TcpEventFactory implements EventFactory {

	//CLOSE_CONNECTION(TcpCloseConnectionEvent::new),
	CONNECTION_EXCEPTION((source, cause) -> {
		TcpConnectionEventSource eventSource = new TcpConnectionEventSource((SocketInfo)source,cause);
		return new TcpConnectionExceptionEvent(eventSource);
	}),
	OPEN_CONNECTION((source, cause) -> {
		TcpConnectionEventSource eventSource = new TcpConnectionEventSource((SocketInfo)source,cause);
		return new TcpOpenConnectionEvent(eventSource);
	}),
	SERVER_EXCEPTION((source, cause) -> {
		TcpServerEventSource eventSource = new TcpServerEventSource((TcpServerListener)source,cause);
		return new TcpServerExceptionEvent(eventSource);
	}),
	CORRELATION_CONNECTION_EXCEPTION((source, cause) -> {
		TcpConnectionCorrelationFailedEventSource eventSource = new TcpConnectionCorrelationFailedEventSource((String)source,cause);
		return new TcpConnectionCorrelationFailedEvent(eventSource);
	});
	
	private TcpEventFactory(EventFactory factory) {
		this.factory = factory;
	}
	
	private EventFactory factory;

	@Override
	public TcpEvent create(Object source, Throwable cause) {
		return factory.create(source,cause);
	}
}
