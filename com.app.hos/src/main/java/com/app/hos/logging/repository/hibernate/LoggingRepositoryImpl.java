package com.app.hos.logging.repository.hibernate;

import java.util.Collection;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.logging.model.Log;
import com.app.hos.logging.repository.LoggingRepository;

@Repository
@Transactional
public class LoggingRepositoryImpl implements LoggingRepository {

	@PersistenceContext(unitName = "sqlite_persistance")
	private EntityManager manager;

	@Transactional(value ="sqliteJpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
	public void save(Log log) {
		if (log.getId() != null) {
			throw new IllegalArgumentException("Try to save existing 'Log' message");
		}
		manager.persist(log);
	}
	
	@Transactional(value ="sqliteJpaTransactionManager",readOnly = true)
	public Collection<Log> findLogForLevel(Level level) {
		TypedQuery<Log> query = manager.createQuery("SELECT l FROM Log l WHERE l.level = :level ORDER BY l.time ASC", Log.class);
		return query.setParameter("level", level.getName()).getResultList();
	}
	
	@Transactional(transactionManager ="sqliteJpaTransactionManager",readOnly = true)
	public Collection<Log> findAll() {
		TypedQuery<Log> query = manager.createQuery("SELECT l FROM Log l",Log.class);
		return query.getResultList();
	}
	
	@Transactional(value ="sqliteJpaTransactionManager",readOnly = true)
	public Collection<Log> findLogForDevice(String serial) {
		TypedQuery<Log> query = manager.createQuery("SELECT l FROM Log l WHERE l.serial = ?",Log.class);
		return query.setParameter(1, serial).getResultList();
	}
}
