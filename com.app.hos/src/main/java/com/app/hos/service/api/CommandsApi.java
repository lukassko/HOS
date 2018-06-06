package com.app.hos.service.api;

import org.springframework.messaging.MessageHeaders;

import com.app.hos.share.command.CommandInfo;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.type.CommandType;


public interface CommandsApi {

	public void receivedCommand(MessageHeaders headers, Command command);
	
	public void sendCommand(CommandInfo command);
	
	public void sendCommand(String connectionId, CommandType type);
	
	public void sendCommand(String connectionId, Command command);

}
