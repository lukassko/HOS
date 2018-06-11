package com.app.hos.share.command;

import com.app.hos.share.command.builder_v2.Command;

public class CommandInfo {

	private String connectionId;
	
	private int deviceId;
	
	private Command command;

	public CommandInfo(String connectionId, Command command) {
		this(connectionId,command,0);
	}
	
	public CommandInfo(String connectionId, Command command, int deviceId) {
		this.connectionId = connectionId;
		this.command = command;
		this.deviceId = deviceId;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
	
	

}
