package com.app.hos.model.command;

import java.io.Serializable;

public class Command implements Serializable{

	private final String clientId;
	private final String clientName;
	private String comandType;
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
	
	public String getComandType() {
		return comandType;
	}
	
	public void setComandType(String comandType) {
		this.comandType = comandType;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
