package com.app.hos.server.event;

import com.app.hos.server.connection.Connection;

public interface EventFactory {

	public default TcpEvent create(Connection connection) {
		return this.create(connection, null);
	}

	public default TcpEvent create(Throwable throwable) {
		return this.create(null, throwable);
	}
	
	public TcpEvent create(Connection connection, Throwable throwable);
	
}
