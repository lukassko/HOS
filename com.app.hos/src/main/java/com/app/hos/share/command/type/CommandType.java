package com.app.hos.share.command.type;

public enum CommandType {
	
	HELLO(false),
	MY_STATUS(false),
	BAD_CONVERSION(false);
	
	private boolean isExecutable;
	
	private CommandType(boolean isExecutable) {
		this.isExecutable = isExecutable;
	}
	
	public boolean isExecutable(){
		return this.isExecutable;
	}
}
