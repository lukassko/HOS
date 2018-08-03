package com.app.hos.service.aspects;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.service.command.result.NewDevice;
import com.app.hos.service.managers.DeviceManager;

/*
 *  Log information about new and closed connections
 */
@Aspect
public class ConnectionAspect extends Logger {
	
	@Autowired
	private DeviceManager deviceManager;
	
	public enum ConnectionEvent {
		OPEN,CLOSE
	}
	
	public ConnectionAspect(LoggingRepository repository) {
		super(repository);
	}
	
	@Pointcut("execution(* com.app.hos.service.managers.DeviceManager.openDeviceConnection(..)) && args(connectionId,newDevice)")
	public void newConnectionPointcut(String connectionId, NewDevice newDevice) {}
	
	@Pointcut("execution(* com.app.hos.service.api.SystemFacade.closeConnection(..)) && args(connectionId)")
	public void closeConnectionPointcut(String connectionId) {}

	@Before("newConnectionPointcut(connectionId,newDevice)")
	public void newConnectionPointcutImpl(JoinPoint point,String connectionId, NewDevice newDevice) {
		logAndSaveMessage(point, Level.INFO, newDevice.getSerialId(), getConnectionLog(ConnectionEvent.OPEN,newDevice));
	}

	@Before("closeConnectionPointcut(connectionId)")
	public void closeConnectionPointcutImpl(JoinPoint point, String connectionId) {
		Device device = deviceManager.findDeviceByConnection(connectionId);
		logAndSaveMessage(point, Level.INFO, device.getSerial(),getConnectionLog(ConnectionEvent.CLOSE,device.getConnection()));
	}

	private String getConnectionLog(ConnectionEvent event, NewDevice device) {
		StringBuilder message = new StringBuilder();
		message.append("Connection " + event.toString());
		message.append("; IP " + device.getIp());
		message.append("; Port " + device.getPort());
		return message.toString();
	}
	
	private String getConnectionLog(ConnectionEvent event, Connection connection) {
		StringBuilder message = new StringBuilder();
		message.append("Connection " + event.toString());
		message.append("; IP " + connection.getIp());
		message.append("; Port " + connection.getRemotePort());
		return message.toString();
	}
}
