package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;

import com.app.hos.logging.LoggingRepository;
import com.app.hos.share.utils.DateTime;

public class Logger {

	private LoggingRepository repository;
	
	public Logger(LoggingRepository repository){
		this.repository = repository;
	}
	
	protected void logAndSaveMessage(JoinPoint point, Level level, String serial,String message) {
		logMessage(point,level,serial,message);
		saveLog(level,serial,message);
	}
	
	protected void logMessage(JoinPoint point, Level level, String serial,String message) {
		String msg = createLogMessage(serial,message);
		getLogger(point).log(level, msg);
	}

	protected void saveLog(Level level, String serial,String message) {
		repository.save(new DateTime().getTimestamp(), level.toString(), serial, message);
	}
	
	private java.util.logging.Logger getLogger(JoinPoint point) {
		return java.util.logging.Logger.getLogger(point.getTarget().getClass().getName());
	}
	
	private String createLogMessage(String serial, String message) {
		return "Device " + serial + "; Message: " + message;
	}

}
