package com.app.hos.share.command;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.result.Result;

public class HelloCommandBuilder extends AbstractCommandBuilder {

	  @Override
	    public void setCommandType() {
	        String type = CommandType.HELLO.toString();
	        command.setCommandType(type);
	    }

	    @Override
	    public void setResult() {
	    	List<Result> result = new LinkedList<Result>();
	    	NewDevice newDevice = new NewDevice(getName());
	    	result.add(newDevice);
	        command.setResult(result);
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
