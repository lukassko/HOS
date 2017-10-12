package com.app.hos.service.managers.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.type.CommandType;

@Service
public class CommandManager {

	//private TaskStrategy taskStrategy; inject
	//@Autowired
	//private DeviceManager deviceManager;
	private final int threadCount = 3;
	
	private ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
	
	public void executeCommand(MessageHeaders headers, Command command) {
		CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) {
			//executeTask(command);
		} else {
			
		}
	}

	public Message<Command> createMessage(String connectionId, CommandType type) {
		Command command = CommandFactory.getCommand(type);
		Message<Command> message = MessageBuilder.withPayload(command)
		        .setHeader(IpHeaders.CONNECTION_ID, connectionId)
		        .build();		
		return message;
	}
}
