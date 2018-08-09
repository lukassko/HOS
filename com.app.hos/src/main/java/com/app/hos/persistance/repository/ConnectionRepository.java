package com.app.hos.persistance.repository;

import java.util.List;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;

public interface ConnectionRepository {

	public Connection find(String connectionId);
	
	public void save(HistoryConnection connection);
	
	// TODO consider to separate repository
	public List<HistoryConnection> findHistoryConnectionsForDevice(int id); 
	
	public List<HistoryConnection> findHistoryConnectionsForPeriod(DateTime from, DateTime to); 
	
}
