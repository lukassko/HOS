package com.app.hos.share.command;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.result.NewDevice.DeviceType;
import com.app.hos.share.command.type.CommandType;

public class HelloCommandBuilder extends AbstractCommandBuilder {

	@Override
	public void setCommandType() {
	    String type = CommandType.HELLO.toString();
	    command.setCommandType(type);
	}

	@Override
	public void setResult() {
	  	NewDevice newDevice = new NewDevice(getName(),getType());
	    command.setResult(newDevice);
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
}
