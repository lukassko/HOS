package com.app.hos.service.managers.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.app.hos.service.managers.device.DeviceCreator;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.task.TaskExecutor;
import com.app.hos.share.command.task.TaskStrategy;

@Component
public class CommandManager implements CommandExecutor {

	private DeviceManager deviceManager;
	private TaskStrategy taskStrategy;
	
	@Autowired
	public CommandManager(DeviceManager DeviceManager, TaskStrategy taskStrategy) {
		this.deviceManager = DeviceManager;
		this.taskStrategy = taskStrategy;
	}
	
	public void executeCommand(MessageHeaders headers, Command command) {
		CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) 
			executeTask(command);
		else 
			getCommandResult(type,headers,command);			
	}

	private void executeTask(TaskExecutor taskExecutor) {
		this.taskStrategy.setExecutor(taskExecutor);
		taskStrategy.executeTask();
	}
	
	private void getCommandResult(CommandType type,MessageHeaders headers,Command command) {
		if (CommandType.BAD_CONVERSION == type) {
			
			
		} else {
			//List<Result> commandResults = command.getResult();
			if (CommandType.HELLO == type) {
				
			} else {
				
			}
		}
		
	}
}
