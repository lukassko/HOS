package com.app.hos.service.websocket.command.builder;

import java.io.Serializable;

import com.app.hos.service.websocket.command.type.WebCommandType;

public class WebCommand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
		
	private String message;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "WebCommand [type=" + type + ", message=" + message + "]";
	}

}
