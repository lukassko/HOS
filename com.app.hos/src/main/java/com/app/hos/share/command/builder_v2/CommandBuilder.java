package com.app.hos.share.command.builder_v2;

// TODO: thread safe ??
public class CommandBuilder {

    private AbstractCommandBuilder commandBuilder;

    public void setCommandBuilder(AbstractCommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public Command createCommand () {
        return commandBuilder.createCommand()
		        				.setCommandType()
		        				.setResult()
		        				.setStatus().getCommand();
    }
}
