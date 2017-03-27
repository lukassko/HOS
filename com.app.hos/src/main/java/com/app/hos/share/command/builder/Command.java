package com.app.hos.share.command.builder;

import java.io.Serializable;
import java.util.List;

import com.app.hos.share.command.task.TaskExecutor;

public class Command implements Serializable, TaskExecutor {

	private static final long serialVersionUID = 1L;
	
	private String clientName;
	private String commandType;
	private List<Result> result;

	public void setClientName (String clientName) {
		this.clientName = clientName;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public String getCommandType() {
		return commandType;
	}
	
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
	}

	public boolean executeTask() {
		return false;
	}
}
