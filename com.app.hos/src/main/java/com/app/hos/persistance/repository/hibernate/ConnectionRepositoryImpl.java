package com.app.hos.persistance.repository.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.connection.HistoryConnection;
import com.app.hos.persistance.repository.ConnectionRepository;

@Repository
public class ConnectionRepositoryImpl implements ConnectionRepository {

	@PersistenceContext
	private EntityManager manager;
	
	// CONNECTION

	public Connection find(String connectionId) {
		try {
			TypedQuery<Connection> query = manager.createQuery("SELECT c FROM Connection c WHERE c.connectionId = :connectionId", Connection.class);
			return query.setParameter("connectionId", connectionId).getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	// HISTORY CONNECTION
	
	public void save(HistoryConnection connection) {
		if(connection.isNew()) {
			manager.persist(connection);
		} else {
			manager.merge(connection);
		}
	}
	
	public List<HistoryConnection> findHistoryConnectionsForDevice(int id) {
		TypedQuery<HistoryConnection> query = manager.createQuery("SELECT c FROM HistoryConnection c WHERE c.deviceId = :id ORDER BY c.endConnectionTime ASC", HistoryConnection.class);
		return query.setParameter("id", id).getResultList();
	}

	// find all connections which starts between given periods
	public List<HistoryConnection> findHistoryConnectionsForPeriod(DateTime from, DateTime to) {
		String queryStr = "SELECT hc FROM HistoryConnection hc WHERE hc.beginConnectionTime >= :from AND hc.beginConnectionTime <= :to";
		TypedQuery<HistoryConnection> query = manager.createQuery(queryStr, HistoryConnection.class);
		query.setParameter("from", from).setParameter("to", to);
		return query.getResultList();
	}

}
