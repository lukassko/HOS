package com.app.hos.service.integration.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.service.integration.converters.ConnectionIdTransforner;
import com.app.hos.service.managers.command.CommandExecutor;
import com.app.hos.share.command.CommandBuilder;
import com.app.hos.share.command.HelloCommandBuilder;
import com.app.hos.share.command.builder.Command;

public class Server {
	
	private ConnectionIdTransforner connectionIdTransformer;
	private CommandExecutor commandExecutor;
	private Gateway gateway;
	private CommandBuilder commandBuilder = new CommandBuilder();
	
	@Autowired
	public Server(Gateway gateway, CommandExecutor commandExecutor) {
		this.gateway = gateway;
		this.commandExecutor = commandExecutor;
	}

	public void receiveCommand(Message<Command> message) {
		Command command = message.getPayload();
		commandExecutor.executeCommand(message.getHeaders(),command);
		
		commandBuilder.setCommandBuilder(new HelloCommandBuilder());
        commandBuilder.createCommand();
        Command cmd = commandBuilder.getCommand();
		MessageHeaders h = message.getHeaders();
		Message<Command> tmp = MessageBuilder.withPayload(cmd)
		        .setHeader(IpHeaders.CONNECTION_ID, h.get(IpHeaders.CONNECTION_ID))
		        .build();		
		sendMessage(tmp);
	}
		
	public void sendMessage(Message<Command> message) {
		if(message != null) {
			this.gateway.send(message);
		} else {
			System.out.println("EMPTY MSG");
		}
	}
		
	
}
