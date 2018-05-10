package com.app.hos.share.command.builder.concretebuilders;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;

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
	  	String name;
	    try {
	        InetAddress localMachine = java.net.InetAddress.getLocalHost();
	        name = localMachine.getHostName();
	    } catch (UnknownHostException e) {
	      	name = "Unknown";
	    }
	    return name;
	}
	
	@Override
	public AbstractCommandBuilder setStatus() {
		command.setStatus(true);
		return this;
	}

}
