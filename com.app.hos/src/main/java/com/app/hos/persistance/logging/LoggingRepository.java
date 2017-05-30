package com.app.hos.persistance.logging;

public interface LoggingRepository {

	public void save(long timestamp, String level, String message);

}
