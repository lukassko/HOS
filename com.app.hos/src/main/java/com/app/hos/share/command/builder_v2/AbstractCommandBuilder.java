package com.app.hos.share.command.builder_v2;

public abstract class AbstractCommandBuilder {

    protected Command command;
    
    public AbstractCommandBuilder() {}
	
    public Command getCommand() {
        return this.command;
    }
    
    public AbstractCommandBuilder createCommand () {
        this.command = new Command();
        return this;
    }

	public abstract AbstractCommandBuilder setResult();
	
    public abstract AbstractCommandBuilder setCommandType();
	
	public abstract AbstractCommandBuilder setStatus();
	
}
