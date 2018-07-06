package com.app.hos.server.event;

import org.springframework.context.ApplicationEvent;


public abstract class TcpEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final Throwable cause;

	public TcpEvent(Object source) {
		super(source);
		this.cause = null;
	}

	public TcpEvent(Object source, Throwable cause) {
		super(source);
		this.cause = cause;
	}
	
	public Throwable getCause() {
		return cause;
	}
	
	@Override
	public String toString() {
		return "TcpEvent [source=" + this.getSource() +
				(this.cause == null ? "" : ", cause=" + this.cause) + "]";
	}

}
