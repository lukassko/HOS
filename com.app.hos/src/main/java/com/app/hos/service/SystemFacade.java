package com.app.hos.service;

import javax.websocket.Session;

import org.springframework.messaging.MessageHeaders;

import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.type.CommandType;

public interface SystemFacade {
	
	public void receivedCommand(MessageHeaders headers, Command command);
	
	public void sendCommand(String connectionId, CommandType type);
	
	public void sendCommand(String connectionId, Command command);
	
	public void sendWebCommand(Session session, String message);
	
	public boolean closeConnection(String connectionId);
	
	public void removeDevice(String serial);
}
