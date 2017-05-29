package com.app.hos.persistance.logging.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.logging.LoggingRepository;
import com.app.hos.share.utils.DateTime;


@Transactional
@Repository
public class LoggingRepositoryImpl implements LoggingRepository {

	@PersistenceContext
	private EntityManager manager;
	
	public void save(DateTime time, String level, String message) {
		
	}
}
