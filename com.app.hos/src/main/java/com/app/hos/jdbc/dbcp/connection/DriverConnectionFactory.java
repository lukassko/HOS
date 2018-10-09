package com.app.hos.jdbc.dbcp.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class DriverConnectionFactory implements ConnectionFactory {

	private String connectionString;
	
	private Driver driver;
	
	private Properties properties;
	
	public DriverConnectionFactory(Driver driver, String connectionString,Properties properties) {
		this.driver = driver;
		this.connectionString = connectionString;
		this.properties = properties;
	}

	@Override
	public Connection createConnection() throws SQLException {
		return driver.connect(connectionString, properties);
	}
}
