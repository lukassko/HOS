package com.app.hos.service.websocket.command.builder;

import java.io.Serializable;

import com.app.hos.service.websocket.command.type.WebCommandType;

public class WebCommand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private WebCommandType type;
		
	private String message;
	
	public WebCommandType getType() {
		return this.type;
	}

	public void setType(WebCommandType type) {
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
