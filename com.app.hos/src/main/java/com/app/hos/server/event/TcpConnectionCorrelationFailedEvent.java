package com.app.hos.server.event;

import com.app.hos.server.event.source.TcpEventSource;

public class TcpConnectionCorrelationFailedEvent extends TcpEvent {

	private static final long serialVersionUID = 1L;

	public TcpConnectionCorrelationFailedEvent(TcpEventSource source) {
		super(source);
	}
	
	@Override
	public String toString() {
		return super.toString() + " **EXCEPTION**";
	}
}
