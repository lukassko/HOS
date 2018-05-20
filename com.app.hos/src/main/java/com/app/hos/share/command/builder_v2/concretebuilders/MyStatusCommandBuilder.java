package com.app.hos.share.command.builder_v2.concretebuilders;

import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.share.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.share.command.builder_v2.CommandDescriptor;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.Utils;

@CommandDescriptor(
		device={DeviceType.SERVER, DeviceType.PHONE, DeviceType.TV}, 
		type=CommandType.GET_STATUS
	)
public class MyStatusCommandBuilder extends AbstractCommandBuilder {

	@Override
	public AbstractCommandBuilder setCommandType() {
		String type = CommandType.MY_STATUS.toString();
		command.setCommandType(type);
		return this;
	}

	@Override
	public AbstractCommandBuilder setResult() {
		double cpu = Utils.getCpuUsage();
		double ram = Utils.getRamUsage();
		DeviceStatus status = new DeviceStatus(ram,cpu);
		command.setResult(status);
		return this;
	}
	
	@Override
	public AbstractCommandBuilder setStatus() {
		command.setStatus(true);
		return this;
	}
	
}
