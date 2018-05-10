package com.app.hos.share.command.builder;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.app.hos.share.command.result.Message;


public abstract class AbstractCommandBuilder {

    protected Command command;
    protected String exceptionMessage = null;
    
    public AbstractCommandBuilder() {}
	
	public AbstractCommandBuilder(String message) {
		this.exceptionMessage = message;
	}
	
    public Command getCommand() {
        return this.command;
    }
    
    public AbstractCommandBuilder createCommand (String clientId, String clientName) {
        String serverName;
        try {
            InetAddress localMachine = java.net.InetAddress.getLocalHost();
            serverName = localMachine.getHostName();
        } catch (UnknownHostException e) {
            serverName = "Unknown";
        }
        this.command = new Command();
        command.setSerialId(serverName);
        return this;
    }

	public AbstractCommandBuilder setResult() {
		this.command.setResult(new Message(exceptionMessage));
		return this;
	};
	
    public abstract AbstractCommandBuilder setCommandType ();
	
	public abstract AbstractCommandBuilder setStatus();
	
}
