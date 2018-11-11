package com.app.hos.jdbc.dbcp.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.app.hos.jdbc.dbcp.connection.DelegatingConnection;

public class PoolableConnection extends DelegatingConnection<Connection> {

	private static final String DISCONNECTION_SQL_CODE_PREFIX = "08";
	
	private static final Set<String> DISCONNECTION_SQL_CODES;
	
	static {
        DISCONNECTION_SQL_CODES = new HashSet<>();
        DISCONNECTION_SQL_CODES.add("57P01"); // ADMIN SHUTDOWN
        DISCONNECTION_SQL_CODES.add("57P02"); // CRASH SHUTDOWN
        DISCONNECTION_SQL_CODES.add("57P03"); // CANNOT CONNECT NOW
        DISCONNECTION_SQL_CODES.add("01002"); // SQL92 disconnect error
        DISCONNECTION_SQL_CODES.add("JZ0C0"); // Sybase disconnect error
        DISCONNECTION_SQL_CODES.add("JZ0C1"); // Sybase disconnect error
    }
	
	private final ObjectPool<PoolableConnection> pool;
	
	private volatile boolean closed;
	
	private volatile boolean fatalSqlExceptionThrows = false;
	
	private PreparedStatement validationPreparedStatement;
	private String lastValidationSql;
	
	public PoolableConnection(final Connection connection, final ObjectPool<PoolableConnection> pool){
		super(connection);
		this.pool = pool;
	}

	public void activate() {
		this.closed = false;
		setLastUsed();
	}
	
	/**
	 * Should return connection to pool
	 */
	public synchronized void close() throws SQLException {
		if (isClosedInternal()) {
			// already close
			return;
		}
		
		boolean isUnderlyingConnectionClosed;
		
		try {
			isUnderlyingConnectionClosed = this.getDelegateInternal().isClosed();
		} catch (SQLException e) {
			try {
				pool.invalidateObject(this); // remove from pool
			} catch (Exception ie) {
				passivateAdnClose();
			}
			
			throw new SQLException("Cannot close connection (isClosed throw exception", e);
		}
		
		if (isUnderlyingConnectionClosed) {
			// Abnormal close: underlying connection close unexpectedly
			// must destroy this proxy
			try {
				this.pool.invalidateObject(this);
			} catch (Exception ie) {
				passivateAdnClose();
			}
		} else {
			// Normal close: return proxy to pool
			try {
				this.pool.returnObject(this);
			} catch (Exception e) {
				
			}
		}
		
	}
	
	private void passivateAdnClose() throws SQLException {
		passivate();
		getInnermostDelegate().close();
	}
	
	protected void passivate() throws SQLException {
		super.passivate();
		super.setClosedInternal(true);
	}
	
	public void reallyClose() throws SQLException {
		if (validationPreparedStatement != null) {
			validationPreparedStatement.close();
		}
		super.close();
	}
	
	/**
	 * Verify if fatal sql exception was throws, if yes close connection
	 * Execute test query to check if connection is not broken, 
	 * 		executing SELECT statement should return at lest one row
	 * If sql is null o empty, call isValid method from Connection
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

	protected void handleExcetion(SQLException e) throws SQLException {
		fatalSqlExceptionThrows |= isFatalException(e);
		super.handleException(e);
	}
	
	/**
	 * Every sql exception which code contain DISCONNECTION_SQL_CODES 
	 * is treated as fatal exception. When such exception occur
	 * the connection should be closed
	 */
	private boolean isFatalException(SQLException e) {
		boolean fatalException = false;
		String state = e.getSQLState();
		if (state != null) {
			fatalException = state.startsWith(DISCONNECTION_SQL_CODE_PREFIX) 
										|| DISCONNECTION_SQL_CODES.contains(state);
			if (!fatalException) {
				SQLException nextException = e.getNextException();
				if (nextException != null && nextException != e) {
					fatalException = isFatalException(nextException);
				}
			}
		}
		return fatalException;
	}
}
