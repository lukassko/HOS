package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.app.hos.persistance.logging.LoggingRepository;

@Aspect
public class PersistanceAspect extends Logger {

	public PersistanceAspect(LoggingRepository repository) {
		super(repository);
	}
	
	@Before("within(com.app.hos.persistance.repository.hibernate.DeviceRepository*)")
	public void logTest(JoinPoint point) {
		logMessage(point, Level.INFO, point.toString());
		saveLog(Level.INFO,point.toString());
	}

}
