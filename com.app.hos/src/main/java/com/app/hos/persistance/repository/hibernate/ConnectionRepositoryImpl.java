package com.app.hos.persistance.repository.hibernate;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.persistance.repository.ConnectionRepository;
import com.app.hos.share.utils.DateTime;

@Repository
public class ConnectionRepositoryImpl implements ConnectionRepository {

	@PersistenceContext//(unitName="myslq_persistance")
	private EntityManager manager;
	
	// CONNECTION

	public Connection findConnectionById(String connectionId) {
		TypedQuery<Connection> query = manager.createQuery("SELECT c FROM Connection c WHERE c.connectionId = :connectionId", Connection.class);
		return query.setParameter("connectionId", connectionId).getSingleResult();
	}

	// HISTORY CONNECTION
	
	public void save(HistoryConnection connection) {
		if(connection.isNew()) {
			manager.persist(connection);
		} else {
			manager.merge(connection);
		}
	}
	
	public Collection<HistoryConnection> findAllHistoryConnectionsByDeviceId(int id) {
		TypedQuery<HistoryConnection> query = manager.createQuery("SELECT c FROM HistoryConnection c WHERE c.deviceId = :id ORDER BY c.endConnectionTime ASC", HistoryConnection.class);
		return query.setParameter("id", id).getResultList();
	}

	public Collection<HistoryConnection> findHistoryConnectionsForTimePeriod(DateTime from, DateTime to) {
		// TODO Auto-generated method stub
		return null;
	}

}
