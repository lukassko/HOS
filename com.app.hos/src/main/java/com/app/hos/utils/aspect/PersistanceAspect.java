package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.app.hos.logging.LoggingRepository;
import com.app.hos.persistance.models.Device;

@Aspect
public class PersistanceAspect extends Logger {

	public PersistanceAspect(LoggingRepository repository) {
		super(repository);
	}

	@Pointcut("execution(* com.app.hos.persistance.repository.DeviceRepository.save(..)) && args(device)")
	public void addNewDevicePointcut(Device device) {}

	@Before("addNewDevicePointcut(device)")
	public void addNewDevice(JoinPoint point,Device device) {
		if (device.isNew()) {
			logAndSaveMessage(point, Level.INFO, device.getSerial(),"New device added");
		} 
	}
	
//	@AfterThrowing(pointcut="databaseExceptionPointcut()", throwing="exception")
//	public void logException(JoinPoint point, Throwable exception) {
//		//logAndSaveMessage(point, Level.WARNING,exception.getMessage());
//	}

}
