package com.app.hos.server.event;

public interface EventFactory {

	public default TcpEvent create(Object cause) {
		return this.create(cause, null);
	}

	public TcpEvent create(Object cause, Throwable throwable);
	
}
