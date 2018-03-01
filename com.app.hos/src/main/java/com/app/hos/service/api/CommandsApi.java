package com.app.hos.service.api;

import org.springframework.messaging.MessageHeaders;

import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.type.CommandType;


public interface CommandsApi {

	public void receivedCommand(MessageHeaders headers, Command command);
	
	public void sendCommand(String connectionId, CommandType type);
	
	public void sendCommand(String connectionId, Command command);

}
