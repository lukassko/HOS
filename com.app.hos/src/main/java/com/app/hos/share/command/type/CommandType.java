package com.app.hos.share.command.type;

//TODO:
//remove JSON_EXCEPTION and EXECUTION_EXCEPTION, its only reason why command cannot be executed
//use 'status' field by set true/false and set message with exception type if occure
public enum CommandType {
	
	HELLO(false),
	MY_STATUS(false),
	BAD_COMMAND_CONVERSION(false),
	EXECUTION_EXCEPTION(false),
	UNKNOWN_COMMAND(false),
	GET_STATUS(true);
	
	private boolean isExecutable;
	
	private CommandType(boolean isExecutable) {
		this.isExecutable = isExecutable;
	}
	
	public boolean isExecutable(){
		return this.isExecutable;
	}
}
