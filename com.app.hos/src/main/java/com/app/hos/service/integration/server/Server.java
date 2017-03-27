package com.app.hos.service.integration.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.app.hos.service.managers.command.CommandExecutor;
import com.app.hos.share.command.builder.Command;

public class Server {
	

	private CommandExecutor commandExecutor;
	private Gateway gateway;
	
	@Autowired
	public Server(Gateway gateway, CommandExecutor commandExecutor) {
		this.gateway = gateway;
		this.commandExecutor = commandExecutor;
	}

	public void receiveCommand(Message<Command> message) {
		Command command = message.getPayload();
		commandExecutor.executeCommand(message.getHeaders(),command);
	}
		
	public void sendMessage(Message<Command> message) {
		if(message != null) 
			this.gateway.send("test");
	}
		
	
}
