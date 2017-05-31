package com.app.hos.utils.aspect;

import java.util.logging.Level;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.persistance.logging.LoggingRepository;
import com.app.hos.share.command.builder.Command;

@Aspect
public class ConnectionAspect extends Logger {
	
	public ConnectionAspect(LoggingRepository repository) {
		super(repository);
	}

	@Pointcut("execution(* com.app.hos.service.managers.command.CommandExecutor.executeCommand(..)) && args(headers,command)")
	public void newConnectionPointcut(MessageHeaders headers,Command command) {}
	
//	@Pointcut("within(com.app.hos.service.managers.connection.ConnectionManager.closeConnection) && args(connectionId)")
//	public void lostConnectionPointcut(String connectionId) {}

	@Before("newConnectionPointcut(headers,command)")
	public void logTest(JoinPoint point, MessageHeaders headers,Command command) {
		logAndSaveMessage(point, Level.INFO, point.toString());
	}

}
