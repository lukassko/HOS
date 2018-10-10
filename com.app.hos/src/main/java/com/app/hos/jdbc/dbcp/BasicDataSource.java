package com.app.hos.jdbc.dbcp;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.app.hos.jdbc.dbcp.connection.ConnectionFactory;
import com.app.hos.jdbc.dbcp.connection.DriverConnectionFactory;
import com.app.hos.jdbc.dbcp.pool.GenericObjectPool;
import com.app.hos.jdbc.dbcp.pool.PoolableConnection;
import com.app.hos.jdbc.dbcp.pool.PoolableConnectionFactory;

public class BasicDataSource implements DataSource {

	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private volatile GenericObjectPool<PoolableConnection> connectionPool;
	
	private volatile DataSource dateSource;
	
	private ClassLoader driverClassLoader;
	
	private volatile String driverClassName;
	
	private volatile String url;
	
	private volatile String user;
	
	private volatile String password;
	
	private Properties connectionProperties = new Properties();
	
	private final Object monitor = new Object();
	
	static {
		// Attempt to prevent deadlocks
		DriverManager.getDrivers();
	}
	
	public void setDriverClassName(final String driverClassName) {
    	this.driverClassName = driverClassName;
	}
	
	public void setUrl(final String url) {
        this.url = url;
    }
	
	public void setUser(final String user) {
    	this.user = user;
	}
	
	public void setPassword(final String password) {
        this.password = password;
    }
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private DataSource createDataSource() throws SQLException {
		synchronized (monitor) {
			ConnectionFactory driverConnectionFactory = createConnectionFactory();
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(driverConnectionFactory);
			this.connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
			poolableConnectionFactory.setPool(connectionPool);	
			this.dateSource = new PoolingDataSource<>(connectionPool);
			return this.dateSource;
		}
	}

	private ConnectionFactory createConnectionFactory() throws SQLException {
		Driver driverToUse = null;
		Class<?> driverFromCL = null;
		if (driverClassName != null) {
			try {
				try {
					if (driverClassLoader != null) {
						driverFromCL = Class.forName(driverClassName, true, driverClassLoader);
					} else {
						driverFromCL = Class.forName(driverClassName);
					}
				} catch (ClassNotFoundException e) {
					driverFromCL = Thread.currentThread().getContextClassLoader().loadClass(driverClassName);
				}
			} catch (Exception e) {
				String message = "Cannot load JDBC driver class'" + driverClassName + "'";
				logger.severe(message);
				throw new SQLException(message, e);
			}
		}
		try {
			if (driverFromCL == null) {
				driverToUse = DriverManager.getDriver(url);
			} else {
				driverToUse = (Driver)driverFromCL.newInstance();
				if (!driverToUse.acceptsURL(url)) {
					throw new SQLException("No suitable driver");
				}
			}
		} catch (Exception e) {
			String message = "Cannot create JDBC driver for URL '" + url + "'";
			logger.severe(message);
			throw new SQLException(message, e);
		}

		if (user != null) {
			connectionProperties.put("user", user);
		} else {
			logger.info("DBCP DataSource configured without a 'username'");
		}
		if (password != null) {
			connectionProperties.put("password", password);
		} else {
			logger.info("DBCP DataSource configured without a 'password'");
		}
		return new DriverConnectionFactory(driverToUse, url, connectionProperties);
	}

}
