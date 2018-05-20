package com.app.hos.service.websocket.command.builder_v2;

public abstract class AbstractWebCommandBuilder {

	protected WebCommand command;
	protected String message = null;
	
	public AbstractWebCommandBuilder() {}
	
	public AbstractWebCommandBuilder(String message) {
		this.message = message;
	}
	
    public WebCommand getCommand() {
        return this.command;
    }
    
	public AbstractWebCommandBuilder createCommand () {
		this.command = new WebCommand();
		return this;
	}

	public AbstractWebCommandBuilder setMessage () {
		this.command.setMessage(this.message);
		return this;
	};
	
	public abstract AbstractWebCommandBuilder setCommandType();
	
	public abstract AbstractWebCommandBuilder setStatus ();
	
}
