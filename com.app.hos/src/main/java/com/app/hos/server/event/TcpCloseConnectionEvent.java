package com.app.hos.server.event;

public class TcpCloseConnectionEvent extends TcpConnectionEvent {

	private static final long serialVersionUID = 1L;

	public TcpCloseConnectionEvent(Object source) {
		super(source);
	}

	@Override
	public String toString() {
		return super.toString() + " **CLOSED**";
	}
	
}
