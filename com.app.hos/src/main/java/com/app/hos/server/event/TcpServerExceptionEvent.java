package com.app.hos.server.event;

public abstract class TcpServerExceptionEvent extends TcpEvent {

	private static final long serialVersionUID = 1L;

	public TcpServerExceptionEvent(Object source,Throwable cause) {
		super(source,cause);
	}

}
