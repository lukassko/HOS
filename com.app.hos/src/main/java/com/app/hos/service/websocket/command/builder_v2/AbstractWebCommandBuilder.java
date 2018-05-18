package com.app.hos.service.websocket.command.builder_v2;

public abstract class AbstractWebCommandBuilder {

	protected WebCommand command;
	protected String exceptionMessage = null;
	
	public AbstractWebCommandBuilder() {}
	
	public AbstractWebCommandBuilder(String message) {
		this.exceptionMessage = message;
	}
	
    public WebCommand getCommand() {
        return this.command;
    }
    
	public AbstractWebCommandBuilder createCommand () {
		this.command = new WebCommand();
		return this;
	}

	public AbstractWebCommandBuilder setMessage () {
		this.command.setMessage(this.exceptionMessage);
		return this;
	};
	
	public abstract AbstractWebCommandBuilder setCommandType();
	
	public abstract AbstractWebCommandBuilder setStatus ();
	
}
