package com.app.hos.persistance.logging;

import java.util.Collection;

public interface LoggingRepository {

	public void save(long timestamp, String level, String serial, String message);

	public Collection<String> findLogForLevel(String level);
	
	public Collection<String> findAll();
	
	public Collection<String> findLogForDevice(String serial);
}
