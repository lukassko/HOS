package com.app.hos.service.managers.command;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.service.api.CommandsApi;
import com.app.hos.service.command.CommandInfo;
import com.app.hos.service.command.decorators.FutureCommandDecorator;
import com.app.hos.service.command.future.FutureCommandFactory;
import com.app.hos.service.command.result.Message;
import com.app.hos.service.exceptions.NotExecutableCommandException;

@Service
public class CommandManager {

	private final int THREAD_COUNT = 4; //Integer.getInteger(Utils.getSystemProperty("thread-per-executor"));
	
	@Autowired
	private FutureCommandFactory futureCommandFactory;
	
	@Autowired
	private CommandsApi commandsApi;
	
	public CommandManager() {}
	
	public CommandManager(CommandsApi commandsApi) {
		this.commandsApi = commandsApi;
	}

	private ExecutorService commandExecutor = Executors.newFixedThreadPool(THREAD_COUNT);

	public void executeCommand(CommandInfo command) throws NotExecutableCommandException {
		Callable<CommandInfo> executableCommand = futureCommandFactory.get(command);
		FutureCommandCallback<CommandInfo> futureCallbak = new FutureCommandCallback<CommandInfo>(executableCommand);
		commandExecutor.execute(futureCallbak);
	}
	
	class FutureCommandCallback<V> extends FutureTask<V> {
		
		private CommandInfo commandInfo;
		
		public FutureCommandCallback(Callable<V> callable) {
			super(callable);
			this.commandInfo = ((FutureCommandDecorator)callable).getCommand();
		}
		
		public void done() {
			try {
				this.commandInfo = (CommandInfo)get();
			} catch (Exception e) {
				this.commandInfo.getCommand().setResult(new Message(e.getMessage()));
				this.commandInfo.getCommand().setStatus(false);
			}
			commandsApi.sendCommand(commandInfo);
		}
	}

}
