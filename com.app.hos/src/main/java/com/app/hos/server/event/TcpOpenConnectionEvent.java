package com.app.hos.server.event;

public class TcpOpenConnectionEvent extends TcpEvent {

	private static final long serialVersionUID = 1L;

	public TcpOpenConnectionEvent(Object source) {
		super(source);
	}

	@Override
	public String toString() {
		return super.toString() + " **OPENED**";
	}
	
}
