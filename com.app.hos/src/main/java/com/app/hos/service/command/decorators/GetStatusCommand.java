package com.app.hos.service.command.decorators;

import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.command.CommandInfo;
import com.app.hos.service.command.future.FutureCommand;
import com.app.hos.service.command.result.Result;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.utils.Utils;

@FutureCommand(type = CommandType.GET_STATUS)
public class GetStatusCommand extends FutureCommandDecorator {
	
	public GetStatusCommand(CommandInfo command) {
		super(command);
	}
	
	public CommandInfo call() throws Exception {
		double cpuUsage = Utils.getCpuUsage();
		double ramUsage = Utils.getRamUsage();
		Result statusResult = new DeviceStatus(ramUsage,cpuUsage);
		commandInfo.getCommand().setResult(statusResult);
		return commandInfo;
	}

}
