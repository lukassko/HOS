package com.app.hos.service.websocket.command.builder;

import java.io.Serializable;

public class WebCommand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum WebSocketCommandType {
		REMOVE_DEVICE
	}
	
	private String type;
	private String serial;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
}
