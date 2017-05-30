package com.app.hos.persistance.logging.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.logging.LoggingRepository;


@Transactional
@Repository
public class LoggingRepositoryImpl implements LoggingRepository {

	@PersistenceContext
	private EntityManager manager;
	
	public void save(long timestamp, String level, String message) {
		System.out.println("SAVE! " +level + " "+ message);
		Query query = manager.createNativeQuery("INSERT INTO system_log VALUES (?,?,?); ");
		query.setParameter(1, timestamp);
		query.setParameter(2, level);
		query.setParameter(3, message);
		query.executeUpdate();
	}
}
