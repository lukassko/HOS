package com.app.hos.share.command.builder_v2.concretebuilders;

import com.app.hos.share.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.share.command.builder_v2.CommandDescriptor;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;
import com.app.hos.utils.Utils;

@CommandDescriptor(
		device={DeviceType.SERVER, DeviceType.PHONE, DeviceType.TV}, 
		type=CommandType.HELLO
	)
public class HelloCommandBuilder extends AbstractCommandBuilder {

	@Override
	public AbstractCommandBuilder setCommandType() {
	    String type = CommandType.HELLO.toString();
	    command.setCommandType(type);
	    return this;
	}

	@Override
	public AbstractCommandBuilder setResult() {
	  	NewDevice newDevice = new NewDevice(getName(),getType());
	    command.setResult(newDevice);
	    return this;
	}

	private DeviceType getType() {
		return DeviceType.SERVER;
	}
	
	private String getName() {
	  	return Utils.getHostName();
	}
	
	@Override
	public AbstractCommandBuilder setStatus() {
		command.setStatus(true);
		return this;
	}

}
