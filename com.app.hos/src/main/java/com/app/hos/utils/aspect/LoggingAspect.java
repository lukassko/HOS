package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.app.hos.persistance.logging.LoggingRepository;

@Aspect
public class LoggingAspect extends Logger {
	
	public LoggingAspect(LoggingRepository repository) {
		super(repository);
	}
	
	@Before("execution(* com.app.hos.utils.json.JsonConverter.*(..))")
	public void logTest(JoinPoint point) {
		logMessage(point, Level.INFO, point.toString());
	}

}
