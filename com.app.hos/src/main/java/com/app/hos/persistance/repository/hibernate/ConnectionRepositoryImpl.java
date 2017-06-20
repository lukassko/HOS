package com.app.hos.persistance.repository.hibernate;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.repository.ConnectionRepository;

public class ConnectionRepositoryImpl implements  ConnectionRepository {

	@PersistenceContext
	private EntityManager manager;
	
	public void save(Connection connection) {
		if(connection.isNew()) {
			manager.persist(connection);
		} else {
			manager.merge(connection);
		}
	}

	public Collection<Connection> findAllConnectionsByDeviceId(int id) {
		TypedQuery<Connection> query = manager.createQuery("SELECT c FROM Connection c WHERE c.device_id = :id", Connection.class);
		return query.setParameter("id", id).getResultList();
	}

}
