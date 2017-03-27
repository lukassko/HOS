package com.app.hos.share.command.task;

public class TaskStrategy {

	private TaskExecutor task;
	
	public void setExecutor(TaskExecutor task) {
		this.task = task;
	}
	
	public boolean executeTask() {
		return task.executeTask();
	}
	
	public boolean executeTask (TaskExecutor task) {
		return task.executeTask();
	}

}
