package com.app.hos.service.websocket.command.builder_v2;

import java.io.Serializable;

import com.app.hos.service.websocket.command.WebCommandType;

public class WebCommand implements Serializable {

	private static final long serialVersionUID = 2L;
	
	private WebCommandType type;
	
	private boolean status;
	
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

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return "WebCommand [type=" + this.type + ", status=" + this.status + ", message=" + this.message + "]";
	}

}
