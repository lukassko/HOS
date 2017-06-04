package com.app.hos.service.integration.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.share.command.builder.Command;

@Service 
public class Server {
	
	//private ConnectionIdTransforner connectionIdTransformer;
	private CommandManager commandManager;
	private Gateway gateway;
	
	@Autowired
	public Server(Gateway gateway, CommandManager commandManager) { //, CommandExecutor commandExecutor
		this.gateway = gateway;
		this.commandManager = commandManager;
	}

	public void receiveCommand(Message<Command> message) {
		Command command = message.getPayload();
		commandManager.executeCommand(message.getHeaders(),command);
	}
		
	public void sendMessage(Message<Command> message) {
		if(message != null) 
			this.gateway.send(message);
	}
		
	
}
