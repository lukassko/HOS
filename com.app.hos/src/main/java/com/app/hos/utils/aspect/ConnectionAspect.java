package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.persistance.models.Connection;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.type.CommandType;

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
	
	@Pointcut("execution(* com.app.hos.service.SystemFacade.receivedCommand(..)) && args(headers,command)")
	public void newCommandPointcut(MessageHeaders headers, Command command) {}
	
	@Pointcut("execution(* com.app.hos.service.SystemFacade.closeConnection(..)) && args(connectionId)")
	public void closeConnectionPointcut(String connectionId) {}

	@Before("newCommandPointcut(headers,command)")
	public void newCommandReceive(JoinPoint point,MessageHeaders headers, Command command) {
		CommandType type = command.getEnumeratedCommandType();
		if (type.equals(CommandType.HELLO))
			logAndSaveMessage(point, Level.INFO, command.getSerialId(), getConnectionLogFromHeaders(ConnectionEvent.OPEN,headers));
	}

	@Before("closeConnectionPointcut(connectionId)")
	public void closeConnection(JoinPoint point, String connectionId) {
		//try {
			Connection connection = connectionManager.findConectionByid(connectionId);
			logAndSaveMessage(point, Level.INFO, connection.getDevice().getSerial(),getConnectionLog(ConnectionEvent.CLOSE,connection));
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}

	}

	private String getConnectionLogFromHeaders(ConnectionEvent event, MessageHeaders headers) {
		StringBuilder message = new StringBuilder();
		message.append("Connection " + event.toString());
		message.append("; IP " + headers.get(IpHeaders.IP_ADDRESS));
		message.append("; Port " + (Integer) headers.get(IpHeaders.REMOTE_PORT));
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
