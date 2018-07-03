package com.app.hos.server.factory;

import com.app.hos.server.connection.Connection;

public interface ConnectionFactory {

	public Connection getConnection(String connectionId);
}
