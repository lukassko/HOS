package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.app.hos.persistance.logging.LoggingRepository;
import com.app.hos.persistance.models.Connection;
import com.app.hos.service.managers.connection.ConnectionManager;

@Aspect
public class ConnectionAspect extends Logger {
	
	public enum ConnectionEvent {
		OPEN,CLOSE
	}
	
	public ConnectionAspect(LoggingRepository repository) {
		super(repository);
	}

	@Pointcut("execution(* com.app.hos.service.managers.connection.ConnectionManager.addConnection(..)) && args(connection)")
	public void newConnectionPointcut(Connection connection) {}
	
	@Pointcut("execution(* com.app.hos.service.managers.connection.ConnectionManager.closeConnection(..)) && args(connectionId)")
	public void closeConnectionPointcut(String connectionId) {}

	@Pointcut("within(com.app.hos.service.managers.connection.CloseConnection*)")
	public void test() {}

	@Before("test()")
	public void test(JoinPoint point) {
		System.out.println("TEST");
	}
	
	@Before("newConnectionPointcut(connection)")
	public void newConnection(JoinPoint point, Connection connection) {
		System.out.println("OK1 --------------");
		logAndSaveMessage(point, Level.INFO, connection.getDevice().getSerial(), getConnectionLog(ConnectionEvent.OPEN,connection));
	}

	@Before("closeConnectionPointcut(connectionId)")
	public void closeConnection(JoinPoint point, String connectionId) {
		System.out.println("OK --------------");
		Object target = point.getTarget();
		if (target instanceof ConnectionManager) {
			Connection connection = ((ConnectionManager)target).getConnection(connectionId);
			if (connection != null)
				logAndSaveMessage(point, Level.INFO, connection.getDevice().getSerial(),getConnectionLog(ConnectionEvent.CLOSE,connection));
		}
		
	}

	private String getConnectionLog(ConnectionEvent event, Connection connection) {
		StringBuilder message = new StringBuilder();
		message.append("Connection " + event.toString());
		message.append(";IP " + connection.getIp());
		message.append(";Port " + connection.getRemotePort());
		return message.toString();
	}
}
