package com.app.hos.share.command.builder_v2;


import com.app.hos.share.command.builder.AbstractCommandBuilder;

public class CommandBuilderWrapper<T extends AbstractCommandBuilder> {

	private Class<T> commandBuilderClass;

	public CommandBuilderWrapper(Class<T> commandBuilderClass){
		this.commandBuilderClass = commandBuilderClass;
	}
	
	public Class<T> getCommandBuilderClass() {
		return commandBuilderClass;
	}

	public void setCommandBuilderClass(Class<T> commandBuilderClass) {
		this.commandBuilderClass = commandBuilderClass;
	}
	
	public AbstractCommandBuilder getBuilderInstance() {
		try {
			return commandBuilderClass.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}
}
