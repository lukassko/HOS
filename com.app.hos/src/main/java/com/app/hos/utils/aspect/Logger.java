package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;

import com.app.hos.persistance.logging.LoggingRepository;
import com.app.hos.share.utils.DateTime;

public class Logger {

	private LoggingRepository repository;
	
	public Logger(LoggingRepository repository){
		this.repository = repository;
	}
	
	protected void logAndSaveMessage(JoinPoint point, Level level, String message) {
		logMessage(point,level,message);
		saveLog(level,message);
	}
	
	protected void logMessage(JoinPoint point, Level level, String message) {
		getLogger(point).log(level, message);
	}

	protected void saveLog(Level level, String message) {
		repository.save(new DateTime().getTimestamp(), level.toString(), message);
	}
	
	private java.util.logging.Logger getLogger(JoinPoint point) {
		return java.util.logging.Logger.getLogger(point.getTarget().getClass().getName());
	}

}
