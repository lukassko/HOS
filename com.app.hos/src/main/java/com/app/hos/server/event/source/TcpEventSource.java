package com.app.hos.server.event.source;


public abstract class TcpEventSource {

	public enum EventSource {
		CONNECTION, SERVER;
	}

	protected final Throwable throwable;

	protected final EventSource source;

	public TcpEventSource(EventSource source,Throwable throwable) {
		this.source = source;
		this.throwable = throwable;
	}
	
	public Throwable getThrowable() {
		return this.throwable;
	}

	public EventSource getSource() {
		return this.source;
	}
	
}
