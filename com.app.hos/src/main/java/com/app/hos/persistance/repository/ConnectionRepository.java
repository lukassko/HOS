package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.Connection;

public interface ConnectionRepository {

	public void save(Connection connection);
	
	public Collection<Connection> findAllConnectionsByDeviceId(int id); 
	
}
