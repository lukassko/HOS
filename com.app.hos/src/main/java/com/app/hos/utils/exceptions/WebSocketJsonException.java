package com.app.hos.utils.exceptions;

import javax.websocket.Session;

import com.app.hos.service.websocket.WebCommandCallback;

public class WebSocketJsonException extends HOSException {

	private static final long serialVersionUID = 1L;

	private Session session;
	
	private WebCommandCallback callback;
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public WebCommandCallback getCallback() {
		return callback;
	}

	public void setCallback(WebCommandCallback callback) {
		this.callback = callback;
	}

	public WebSocketJsonException(Throwable throwable) {
		super("Exception while processing Json object",throwable);
		
	}
	
	public WebSocketJsonException(Session session,WebCommandCallback callback,Throwable throwable) {
		super("Exception while processing Json object",throwable);
		this.session = session;
		this.callback = callback;
	}
}
