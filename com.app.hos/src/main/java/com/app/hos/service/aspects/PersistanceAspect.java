package com.app.hos.service.aspects;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.persistance.models.device.Device;

@Aspect
public class PersistanceAspect extends Logger {

	public PersistanceAspect(LoggingRepository repository) {
		super(repository);
	}

	@Pointcut("execution(* com.app.hos.persistance.repository.DeviceRepository.save(..)) && args(device)")
	public void addNewDevicePointcut(Device device) {}

	@Before("addNewDevicePointcut(device)")
	public void addNewDevicePointcutImpl(JoinPoint point,Device device) {
		if (device.isNew()) {
			logAndSaveMessage(point, Level.INFO, device.getSerial(),"New device added.");
		} 
	}
	
	@Pointcut("execution(* com.app.hos.persistance.repository.DeviceRepository.update*(..)) && args(serial,name)")
	public void updateDevicePointcut(String serial, String name) {}
	
	@Before("updateDevicePointcut(serial,name)")
	public void updateDevicePointcutImpl(JoinPoint point,String serial, String name) {
		logAndSaveMessage(point, Level.INFO, serial,"Device was updated.");
	}
	
}
