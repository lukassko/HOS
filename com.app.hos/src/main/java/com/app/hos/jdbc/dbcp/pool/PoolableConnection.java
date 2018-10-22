package com.app.hos.jdbc.dbcp.pool;

import java.sql.Connection;

import com.app.hos.jdbc.dbcp.connection.DelegatingConnection;

public class PoolableConnection extends DelegatingConnection<Connection> {

	private final ObjectPool<PoolableConnection> pool;
	
	public PoolableConnection(final Connection connection, final ObjectPool<PoolableConnection> pool){
		super(connection);
		this.pool = pool;
	}
}
