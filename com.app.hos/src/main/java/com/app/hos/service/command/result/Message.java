package com.app.hos.service.command.result;

import java.io.Serializable;

public class Message implements Result, Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	
    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
