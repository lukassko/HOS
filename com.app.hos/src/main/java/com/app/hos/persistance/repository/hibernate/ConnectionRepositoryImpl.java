package com.app.hos.persistance.repository.hibernate;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.HistoryConnection;
import com.app.hos.persistance.repository.ConnectionRepository;

@Repository
public class ConnectionRepositoryImpl implements  ConnectionRepository {

	@PersistenceContext//(unitName="myslq_persistance")
	private EntityManager manager;
	
	public void save(HistoryConnection connection) {
		if(connection.isNew()) {
			manager.persist(connection);
		} else {
			manager.merge(connection);
		}
	}

	public Collection<HistoryConnection> findAllHistoryConnectionsByDeviceId(int id) {
		TypedQuery<HistoryConnection> query = manager.createQuery("SELECT c FROM HistoryConnection c WHERE c.deviceId = :id", HistoryConnection.class);
		return query.setParameter("id", id).getResultList();
	}

	public Connection findConnectionById(String connectionId) {
		TypedQuery<Connection> query = manager.createQuery("SELECT c FROM Connection c WHERE c.connectionId = :connectionId", Connection.class);
		return query.setParameter("connectionId", connectionId).getSingleResult();
	}

}
