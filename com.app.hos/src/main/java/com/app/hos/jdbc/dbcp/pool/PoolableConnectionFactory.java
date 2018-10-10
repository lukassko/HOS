package com.app.hos.jdbc.dbcp.pool;

import java.sql.Connection;

import com.app.hos.jdbc.dbcp.connection.ConnectionFactory;

public class PoolableConnectionFactory implements PooledObjectFactory<PoolableConnection> {

	private ObjectPool<PoolableConnection> pool;
	
	private ConnectionFactory connectionFactory;
	
	public PoolableConnectionFactory (ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setPool (ObjectPool<PoolableConnection> pool) {
		this.pool = pool;
	}
	
	@Override
	public PooledObject<PoolableConnection> makeObject() throws Exception {
		Connection connection = connectionFactory.createConnection();
		if (connection == null) { // return null if driver is wrongly initialized
			throw new IllegalStateException("Connection factory returned null from createConnection");
		}
		PoolableConnection pc = new PoolableConnection(connection, this.pool);
		return new DefaultPooledObject<PoolableConnection>(pc);
	}

	@Override
	public void destryObject(PooledObject<PoolableConnection> object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
