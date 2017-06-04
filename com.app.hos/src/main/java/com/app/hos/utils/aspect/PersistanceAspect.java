package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.app.hos.persistance.logging.LoggingRepository;

@Aspect
public class PersistanceAspect extends Logger {

	public PersistanceAspect(LoggingRepository repository) {
		super(repository);
	}

	@Pointcut("within(com.app.hos.persistance.repository.hibernate.DeviceRepository*)")
	public void databaseUpdatingPointcut() {}
	
	@Pointcut("execution(* com.app.hos.persistance.repository.DeviceRepository.save(..))")
	public void databaseExceptionPointcut() {}
	
	@Before("databaseUpdatingPointcut()")
	public void logTest(JoinPoint point) {
		System.out.println("PERSISTANCE1");
		//logAndSaveMessage(point, Level.INFO, point.toString());
	}
	
	@AfterThrowing(pointcut="databaseExceptionPointcut()", throwing="exception")
	public void logException(JoinPoint point, Throwable exception) {
		System.out.println("PERSISTANCE3");
		//logAndSaveMessage(point, Level.WARNING,exception.getMessage());
	}

}
