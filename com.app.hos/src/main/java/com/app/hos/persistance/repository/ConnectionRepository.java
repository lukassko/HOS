package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.HistoryConnection;

public interface ConnectionRepository {

	public void save(HistoryConnection connection);
	
	public Collection<HistoryConnection> findAllConnectionsByDeviceId(int id); 
	
}
