package com.app.hos.service.managers.command;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.builder.GenericCommand;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.task.TaskExecutor;

@Component
public class CommandManager implements CommandExecutor {

	private DeviceManager deviceManager;
	//private TaskStrategy taskStrategy; inject
	
	@Autowired
	public CommandManager(DeviceManager DeviceManager) {
		this.deviceManager = DeviceManager;
	}
	
	public void executeCommand(MessageHeaders headers, Command command) {
		CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) {
			//executeTask(command);
		} else {
			//getCommandResult(type,headers,command);		
		}
	}
	
	public <T extends Serializable> void executeGenericCommand(MessageHeaders headers, GenericCommand<T> command) {
		CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) {
			//executeTask(command);
		} else {
			if (CommandType.BAD_CONVERSION == type) {
				
			} else if (CommandType.MY_STATUS == type) {
				DeviceStatus status = (DeviceStatus)command.getResult().get(0);
				getCommandResult(status);
			} else if (CommandType.HELLO == type) {
				NewDevice device = (NewDevice)command.getResult().get(0);
				getCommandResult(device);
			}
			//getGenericCommandResult(type,headers,command);
		}
	}
	
	private void getCommandResult(DeviceStatus result) {
		
	}
	
	private void getCommandResult(NewDevice result) {
		
	}
	

	//private <T> void getGenericCommandResult(CommandType type,MessageHeaders headers,GenericCommand<T> command) {
		//getCommandResult(command);
	//}
	
	//private <T extends DeviceStatus> void getCommandResult(GenericCommand<T> command) {
		
	//}
		
	//private <T extends NewDevice> void getCommandResult(T result) {
		
	//}
	
	
}
