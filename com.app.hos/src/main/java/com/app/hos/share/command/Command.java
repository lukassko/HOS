package com.app.hos.share.command;

import java.io.Serializable;

public class Command implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final String clientId;
	private final String clientName;
	private String commandType;
	private String result;
	
	public Command(String clientId, String clientName) {
		this.clientId = clientId;
		this.clientName = clientName;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public String getCommandType() {
		return commandType;
	}
	
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
