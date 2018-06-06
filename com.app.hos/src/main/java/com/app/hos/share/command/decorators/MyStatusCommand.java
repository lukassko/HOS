package com.app.hos.share.command.decorators;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.share.command.CommandInfo;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.future.FutureCommand;
import com.app.hos.share.command.type.CommandType;

/*
 * add new status to database for device
 */
@FutureCommand(type = CommandType.MY_STATUS)
public class MyStatusCommand extends FutureCommandDecorator {
	
	@Autowired
	private DeviceManager deviceManager;
	
	public MyStatusCommand(CommandInfo command) {
		super(command);
	}
	
	// TODO: by device id, not serial
	public CommandInfo call() throws Exception {
		Command cmd = commandInfo.getCommand();
		DeviceStatus status = (DeviceStatus)cmd.getResult();
		//deviceManager.addDeviceStatus(command.getDeviceId(), status);
		cmd.setStatus(true);
		return commandInfo;
	}

}
