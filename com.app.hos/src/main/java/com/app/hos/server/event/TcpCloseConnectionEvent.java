package com.app.hos.server.event;

import com.app.hos.server.event.source.TcpEventSource;

public class TcpCloseConnectionEvent extends TcpConnectionEvent {

	private static final long serialVersionUID = 1L;

	public TcpCloseConnectionEvent(TcpEventSource source) {
		super(source);
	}

	@Override
	public String toString() {
		return super.toString() + " **CLOSED**";
	}
	
}
