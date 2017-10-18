package com.app.hos.service.managers.command;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.service.SystemFacade;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandConverter;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.exceptions.NotExecutableCommand;

@Service
public class CommandManager {

	private final int THREAD_COUNT = 4;
	
	@Autowired
	private SystemFacade systemFacade;
	
	private ExecutorService commandExecutor = Executors.newFixedThreadPool(THREAD_COUNT);

	public void executeCommand(String connectionId, Command command) throws NotExecutableCommand {
		Callable<Command> executableCommand = CommandConverter.getExecutableCommand(command);
		executeCommand(connectionId,executableCommand);
	}
	
	private void executeCommand(String connectionId, Callable<Command> command) {
		FutureCommandCallback<Command> futureCallbak = new FutureCommandCallback<Command>(connectionId,command);
		commandExecutor.execute(futureCallbak);
	}

	class FutureCommandCallback<V> extends FutureTask<V> {

		private String connectionId;
		
		public FutureCommandCallback(String connectionId,Callable<V> callable) {
			super(callable);
			this.connectionId = connectionId;
		}

		public void done() {
			Command command;
			try {
				command = (Command)get();
			} catch (Exception e) {
				command = CommandFactory.getCommand(CommandType.EXECUTION_EXCEPTION);
				command.setResult(new com.app.hos.share.command.result.Message(e.getMessage()));
				e.printStackTrace();
			}
			systemFacade.sendCommand(connectionId, command);
		}
	}

}
