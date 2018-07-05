package com.app.hos.server.event;

import org.springframework.context.ApplicationEvent;

import com.app.hos.server.connection.Connection;

public abstract class TcpEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public TcpEvent(Object source) {
		super(source);
	}
	
	public String getConnectionId () {
		return ((Connection)this.getSource()).getConnectionId();
	}

	@Override
	public String toString() {
		return "TcpEvent [source=" + source + "]";
	}

}
