package com.app.hos.persistance.logging;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.hos.share.utils.DateTime;

public interface LoggingRepository {


	public void save(DateTime time, String level, String message);
}
