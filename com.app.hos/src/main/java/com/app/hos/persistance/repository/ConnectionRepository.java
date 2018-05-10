package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.share.utils.DateTime;

public interface ConnectionRepository {

	public Connection findConnectionById(String connectionId);
	
	public void save(HistoryConnection connection);
	
	public Collection<HistoryConnection> findAllHistoryConnectionsByDeviceId(int id); 
	
	public Collection<HistoryConnection> findHistoryConnectionsForTimePeriod(DateTime from, DateTime to); 
	
}
