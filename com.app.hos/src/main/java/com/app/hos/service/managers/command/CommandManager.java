package com.app.hos.service.managers.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.share.command.result.NewDevice;

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
			if (CommandType.BAD_CONVERSION == type) {
				System.out.println("BAD CONVERSION");
			} else if (CommandType.HELLO == type) {
				NewDevice device = (NewDevice)command.getResult();
				getCommandResult(headers,device,command.getSerialId());
			} else if (CommandType.MY_STATUS == type) {
				DeviceStatus status = (DeviceStatus)command.getResult();
				getCommandResult(status);
			} 
		}
	}
	
	
	private void getCommandResult(DeviceStatus result) {
		System.out.println("STATUS: " + result.getCpuUsage());
	}
	
	private void getCommandResult(MessageHeaders headers,NewDevice newDevice, String serialId) {
		System.out.println(newDevice.getName()+" | "+serialId);
		deviceManager.createDevice(headers, newDevice.getName(), serialId);
	}	
}
