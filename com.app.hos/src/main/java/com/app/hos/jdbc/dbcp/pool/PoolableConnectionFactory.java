package com.app.hos.jdbc.dbcp.pool;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicLong;

import com.app.hos.jdbc.dbcp.connection.ConnectionFactory;

public class PoolableConnectionFactory implements PooledObjectFactory<PoolableConnection> {

	private ObjectPool<PoolableConnection> pool;
	
	private ConnectionFactory connectionFactory;
	
	private final AtomicLong connectionIndex = new AtomicLong(0);
	
	// configure property
	
	private long maxConnLifetimeMillis = -1;
	
	private int defaultQueryTimeoutSeconds;
	
	private boolean defaultAutoCommit;
	
	private boolean defaultReadOnly;
	
	private volatile String validationQuery;
	
	private int validationQueryTimeout;
	
	private boolean rollbackOnReturn = true;
	
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
	public void destroyObject(PooledObject<PoolableConnection> object) throws Exception {
		object.getObject().close();
	}

	/**
	 *  Rollback transactions, close all related statements and results create by that connection
	 */
	@Override
	public void passivateObject(PooledObject<PoolableConnection> p) throws Exception {
		validateLifeTime(p);
		PoolableConnection conn = p.getObject();
		if (this.rollbackOnReturn) {
			if (!conn.getAutoCommit() && !conn.isReadOnly()) {
				conn.rollback();
			}
		}
		conn.clearWarnings();
		conn.passivate();
	}

	@Override
	public void activateObject(PooledObject<PoolableConnection> object) throws Exception {
		validateLifeTime(object);
		PoolableConnection conn = object.getObject();
        conn.activate();

		// TODO set properties of connection
		
	}
	
	/**
	 *  Validate if connection is not too old
	 *  
	 * @param p
	 * @throws Exception
	 */
	private void validateLifeTime(final PooledObject<PoolableConnection> p) throws Exception {
		if (this.maxConnLifetimeMillis > 0) {
			long lifeTime = System.currentTimeMillis() - p.getCreateTime();
			if (lifeTime > maxConnLifetimeMillis) {
				throw new LifeTimeExceededException("Connection life time exceeded " + lifeTime);
			}
		}
	}

	/**
	 * validate if connection has not thrown a serious sql exception
	 */
	@Override
	public boolean validateObject(PooledObject<PoolableConnection> object) {
		return false;
	}
	
	//  methods to set properties
	
	public void setValidationQuery(final String validationQuery) {
        this.validationQuery = validationQuery;
    }
	
	public void setValidationQueryTimeout(int timeout) {
		this.validationQueryTimeout = timeout;
	}
}
