package com.app.hos.server.event;

import com.app.hos.server.connection.Connection;

@FunctionalInterface
public interface EventFactory {

	public TcpEvent create(Connection connection);
}
