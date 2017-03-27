package com.app.hos.service.managers.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.app.hos.service.managers.device.DeviceCreator;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.task.TaskExecutor;
import com.app.hos.share.command.task.TaskStrategy;

@Component
public class CommandManager implements CommandExecutor {

	private DeviceCreator deviceCreator;
	private TaskStrategy taskStrategy;
	
	@Autowired
	public CommandManager(DeviceCreator deviceCreator, TaskStrategy taskStrategy) {
		this.deviceCreator = deviceCreator;
		this.taskStrategy = taskStrategy;
	}
	
	public void executeCommand(MessageHeaders headers, Command command) {
		this.deviceCreator.addDevice(headers);
		CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) 
			executeTask(command);
	}

	private boolean executeTask(TaskExecutor taskExecutor) {
		this.taskStrategy.setExecutor(taskExecutor);
		return this.taskStrategy.executeTask();
	}
}
