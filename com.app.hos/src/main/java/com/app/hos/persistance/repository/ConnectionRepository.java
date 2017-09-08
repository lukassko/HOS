package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.HistoryConnection;

public interface ConnectionRepository {

	public Connection findConnectionById(String connectionId);
	
	public void save(HistoryConnection connection);
	
	public Collection<HistoryConnection> findAllHistoryConnectionsByDeviceId(int id); 
	
}
