package com.app.hos.logging.repository;

import java.util.Collection;
import java.util.logging.Level;

import com.app.hos.logging.model.Log;

public interface LoggingRepository {

	public void save(Log log);

	public Collection<Log> findLogForLevel(Level level);
	
	public Collection<Log> findAll();
	
	public Collection<Log> findLogForDevice(String serial);
}
