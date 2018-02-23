package com.app.hos.service.exceptions;

import javax.websocket.Session;

public class WebSocketException extends HOSException {

	private static final long serialVersionUID = 1L;
	
	private Session session;
	
	public WebSocketException(Throwable throwable) {
		super(throwable);
	}
	
	public WebSocketException(Session session,Throwable throwable) {
		super(throwable);
		this.session = session;
	}
}
