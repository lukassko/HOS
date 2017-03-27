package com.app.hos.service.managers.command;

import org.springframework.messaging.MessageHeaders;

import com.app.hos.share.command.builder.Command;

public interface CommandExecutor {

	public void executeCommand(MessageHeaders headers, Command command);
	
}
