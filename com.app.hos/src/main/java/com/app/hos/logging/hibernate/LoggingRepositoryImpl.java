package com.app.hos.logging.hibernate;

import java.util.Collection;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.logging.LoggingRepository;


@SuppressWarnings("unchecked")
//@Repository
public class LoggingRepositoryImpl implements LoggingRepository {

	@PersistenceContext(unitName = "sqlite_persistance")
	private EntityManager manager;

	@Transactional(value ="sqliteJpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
	public void save(long timestamp, String level, String serial, String message) {
		Query query = manager.createNativeQuery("INSERT INTO system_log VALUES (?,?,?,?); ");
		query.setParameter(1, timestamp);
		query.setParameter(2, level);
		query.setParameter(3, serial);
		query.setParameter(4, message);
		query.executeUpdate();
	}
	
	@Transactional(value ="sqliteJpaTransactionManager",readOnly = true)
	public Collection<String> findLogForLevel(String level) {
		Query query = manager.createNativeQuery("SELECT * FROM system_log WHERE level = ?");
		query.setParameter(1, level);
		return query.getResultList();
	}
	
	@Transactional(transactionManager ="sqliteJpaTransactionManager",readOnly = true)
	public Collection<String> findAll() {
		Query query = manager.createNativeQuery("SELECT * FROM system_log");
		return query.getResultList();
	}
	
	@Transactional(value ="sqliteJpaTransactionManager",readOnly = true)
	public Collection<String> findLogForDevice(String serial) {
		Query query = manager.createNativeQuery("SELECT * FROM system_log WHERE serial = ?");
		query.setParameter(1, serial);
		return query.getResultList();
	}
}
