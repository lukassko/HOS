package com.app.hos.service.command.decorators;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.hos.service.command.CommandInfo;
import com.app.hos.service.command.builder_v2.Command;
import com.app.hos.service.command.future.FutureCommand;
import com.app.hos.service.command.result.NewDevice;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.managers.DeviceManager;

/*
 *  create new device and send 'Hello' response
 */
@FutureCommand(type = CommandType.HELLO)
public class HelloCommand extends FutureCommandDecorator {
	
	@Autowired
	private DeviceManager deviceManager;
	
	public HelloCommand(CommandInfo commandInfo) {
		super(commandInfo);
	}

	public CommandInfo call() throws Exception {
		Command command = commandInfo.getCommand();
		NewDevice device = (NewDevice)command.getResult();
		deviceManager.openDeviceConnection(commandInfo.getConnectionId(), device);
		//sendCommand(connectionId,type);
		command.setStatus(true);
		return commandInfo;
	}

}
