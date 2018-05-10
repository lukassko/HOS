package com.app.hos.share.command.builder;

// thread safe ??
public class CommandBuilder {

    private AbstractCommandBuilder commandBuilder;

    public void setCommandBuilder(AbstractCommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public synchronized Command createCommand () {
        String clientId = "SERVER_01";
        String clientName = "MAIN_SERVER";
        return commandBuilder.createCommand(clientId, clientName)
		        				.setCommandType()
		        				.setResult()
		        				.setStatus().getCommand();
    }
}
