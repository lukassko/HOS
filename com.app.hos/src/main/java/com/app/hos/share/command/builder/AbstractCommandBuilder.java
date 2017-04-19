package com.app.hos.share.command.builder;

import java.net.InetAddress;
import java.net.UnknownHostException;


public abstract class AbstractCommandBuilder {

    protected Command command;

    public Command getCommand() {
        return this.command;
    }
    
    public void createCommand (String clientId, String clientName) {
        String serverName;
        try {
            InetAddress localMachine = java.net.InetAddress.getLocalHost();
            serverName = localMachine.getHostName();
        } catch (UnknownHostException e) {
            serverName = "Unknown";
        }
        this.command = new Command();
        command.setSerialId(serverName);
    }

    public abstract void setCommandType ();

    public abstract void setResult ();

}
