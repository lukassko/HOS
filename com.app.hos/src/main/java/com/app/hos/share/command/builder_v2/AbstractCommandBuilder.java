package com.app.hos.share.command.builder_v2;

import com.app.hos.utils.Utils;

public abstract class AbstractCommandBuilder {

    protected Command command;
    
    public AbstractCommandBuilder() {}
	
    public Command getCommand() {
        return this.command;
    }
    
    public AbstractCommandBuilder createCommand () {
        String serverName = Utils.getHostName();
        this.command = new Command();
        command.setSerialId(serverName);
        return this;
    }

	public abstract AbstractCommandBuilder setResult();
	
    public abstract AbstractCommandBuilder setCommandType();
	
	public abstract AbstractCommandBuilder setStatus();
	
}
