package com.app.hos.service.integration.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Service;

import com.app.hos.service.api.CommandsApi;
import com.app.hos.service.command.builder_v2.Command;
import com.app.hos.service.exceptions.handler.ExceptionUtils;

@Service 
public class Server {
	
	//private ConnectionIdTransforner connectionIdTransformer;
	@Autowired
	private CommandsApi commandsApi;
	
	@Autowired
	private Gateway gateway;

	public void receiveMessage(Message<Command> message) {
		commandsApi.receivedCommand(message.getHeaders(),message.getPayload());
	}
		
	public void sendMessage(Message<Command> message) {
		try {
			this.gateway.send(message);
		} catch (MessageHandlingException e) {
			ExceptionUtils.handle(e);
		}
	}

}
