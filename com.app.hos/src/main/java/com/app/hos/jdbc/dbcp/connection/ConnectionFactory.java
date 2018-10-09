package com.app.hos.jdbc.dbcp.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

	Connection createConnection() throws SQLException;
	
}
