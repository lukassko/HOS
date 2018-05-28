package com.app.hos.share.command.decorators;

import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.future.FutureCommand;
import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.Utils;

@FutureCommand(type = CommandType.GET_STATUS)
public class GetStatusCommand extends FutureCommandDecorator {
	
	public GetStatusCommand(Command command) {
		super(command);
	}
	
	public Command call() throws Exception {
		double cpuUsage = Utils.getCpuUsage();
		double ramUsage = Utils.getRamUsage();
		Result statusResult = new DeviceStatus(ramUsage,cpuUsage);
		command.setResult(statusResult);
		return command;
	}

}
