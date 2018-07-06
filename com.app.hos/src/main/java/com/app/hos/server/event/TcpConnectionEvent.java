package com.app.hos.server.event;

import com.app.hos.server.connection.Connection;

public abstract class TcpConnectionEvent extends TcpEvent {

	private static final long serialVersionUID = 1L;

	public TcpConnectionEvent(Object source) {
		super(source);
	}

	public String getConnectionId () {
		return ((Connection)this.getSource()).getConnectionId();
	}

}
