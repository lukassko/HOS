package com.app.hos.service.aspects;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.result.NewDevice;

@Aspect
public class ConnectionAspect extends Logger {
	
	@Autowired
	private ConnectionManager connectionManager;
	
	public enum ConnectionEvent {
		OPEN,CLOSE
	}
	
	public ConnectionAspect(LoggingRepository repository) {
		super(repository);
	}
	
	@Pointcut("execution(* com.app.hos.service.managers.DeviceManager.openDeviceConnection(..)) && args(connectionId,device)")
	public void newConnectionPointcut(MessageHeaders headers, Command command) {}
	
	@Pointcut("execution(* com.app.hos.service.api.SystemFacade.closeConnection(..)) && args(connectionId)")
	public void closeConnectionPointcut(String connectionId) {}

	@Before("newConnectionPointcut(connectionId,device)")
	public void newConnectionPointcutImpl(JoinPoint point,String connectionId, NewDevice device) {
		logAndSaveMessage(point, Level.INFO, device.getSerialId(), getConnectionLog(ConnectionEvent.OPEN,device));
	}

	@Before("closeConnectionPointcut(connectionId)")
	public void closeConnectionPointcutImpl(JoinPoint point, String connectionId) {
		Connection connection = connectionManager.findConnection(connectionId);
		logAndSaveMessage(point, Level.INFO, connection.getDevice().getSerial(),getConnectionLog(ConnectionEvent.CLOSE,connection));
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
