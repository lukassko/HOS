package com.app.hos.jdbc.dbcp.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.hos.jdbc.dbcp.connection.DelegatingConnection;

public class PoolableConnection extends DelegatingConnection<Connection> {

	private final ObjectPool<PoolableConnection> pool;
	
	private volatile boolean closed;
	
	private volatile boolean fatalSqlExceptionThrows = false;
	
	private volatile PreparedStatement validationPreparedStatement;
	private volatile String lastValidationSql;
	
	public PoolableConnection(final Connection connection, final ObjectPool<PoolableConnection> pool){
		super(connection);
		this.pool = pool;
	}

	public void activate() {
		this.closed = false;
		setLastUsed();
	}
	
	/**
	 * 
	 * Verify if fatal sql exception was throws, if yes close connection
	 * Execute test query to check if connection is not broken, 
	 * 		executing SELECT statement should return at lest one row
	 * If sql is null o empty, call isValid method from Connection
	 * 
	 */
	public void validate(String sql, int secondsTimeout) throws SQLException {
		if (this.fatalSqlExceptionThrows) {
			throw new SQLException("Connection throws fatal sql exceptions");
		}
		if (sql == null || sql.isEmpty()) {
			if (secondsTimeout < 0) {
				secondsTimeout = 0;
			}
			if (!this.isValid(secondsTimeout)) {
				throw new SQLException("Connection.isValid return false");
			}
			return;
		}
		if (!lastValidationSql.equals(sql)) {
			this.lastValidationSql = sql;
			this.validationPreparedStatement = getDelegateInternal().prepareStatement(sql);
		}
		if (secondsTimeout > 0) {
			this.validationPreparedStatement.setQueryTimeout(secondsTimeout);
		}
		try (ResultSet rs = this.validationPreparedStatement.executeQuery()) {
			if (!rs.next()) {
				throw new SQLException("Validation query do not return any row");
			}
		} catch (SQLException e) {
			throw e;
		}
	}
}
