package com.app.hos.service.managers.command;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.service.SystemFacade;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.share.command.FutureCommandFactory;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.decorators.FutureCommandDecorator;
import com.app.hos.share.command.result.Message;


@Service
public class CommandManager {

	private final int THREAD_COUNT = 4;
	
	@Autowired
	private SystemFacade systemFacade;
	
	// setters
	public void setSystemFacade(SystemFacade systemFacade) {
		this.systemFacade = systemFacade;
	}

	private ExecutorService commandExecutor = Executors.newFixedThreadPool(THREAD_COUNT);

	public void executeCommand(String connectionId, Command command) throws NotExecutableCommandException {
		Callable<Command> executableCommand = FutureCommandFactory.getCommand(command);
		executeCommand(connectionId,executableCommand);
	}
	
	private void executeCommand(String connectionId, Callable<Command> command) {
		FutureCommandCallback<Command> futureCallbak = new FutureCommandCallback<Command>(connectionId,command);
		commandExecutor.execute(futureCallbak);
	}

	class FutureCommandCallback<V> extends FutureTask<V> {

		private String connectionId;
		private Command command;
		
		public FutureCommandCallback(String connectionId,Callable<V> callable) {
			super(callable);
			this.connectionId = connectionId;
			this.command = ((FutureCommandDecorator)callable).getCommand();
		}
		
		public void done() {
			try {
				this.command = (Command)get();
			} catch (Exception e) {
				this.command.setResult(new Message(e.getMessage()));
				this.command.setStatus(false);
			}
			systemFacade.sendCommand(connectionId, command);
		}
	}

}
