package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;

public interface ConnectionRepository {

	public Connection find(String connectionId);
	
	public void save(HistoryConnection connection);
	
	public Collection<HistoryConnection> findHistoryConnectionsForDevice(int id); 
	
	public Collection<HistoryConnection> findHistoryConnectionsForPeriod(DateTime from, DateTime to); 
	
}
