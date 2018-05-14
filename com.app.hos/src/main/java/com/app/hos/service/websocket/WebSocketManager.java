package com.app.hos.service.websocket;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.exceptions.WebSocketJsonException;
import com.app.hos.service.exceptions.handler.ExceptionUtils;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.decorators.FutureWebCommandDecorator;
import com.app.hos.service.websocket.command.future.FutureWebCommandFactory;
import com.app.hos.utils.json.JsonConverter;

@Service
public class WebSocketManager {
	
	@Autowired
	private FutureWebCommandFactory futureWebCommandFactory;
	
	private ExecutorService commandExecutor = Executors.newFixedThreadPool(4);
	
	public void executeCommand(WebCommandCallback callback, Session session, String message) {	
		WebCommand webCommand;
		try {
			webCommand = JsonConverter.getObject(message, WebCommand.class);
			executeCommand(callback,session,webCommand);
		} catch (IOException e) {
			ExceptionUtils.handle(new WebSocketJsonException(session,callback, e));
		}	

	}
	
	// wrapper class get future
	private void executeCommand(WebCommandCallback callback, Session session, WebCommand command) throws NotExecutableCommandException {
		Callable<WebCommand> executableCommand = futureWebCommandFactory.get(command);
		executeCommand(callback,session,executableCommand);
	}
	
	private void executeCommand(WebCommandCallback callback, Session session, Callable<WebCommand> command) {
		FutureCommandCallback<WebCommand> futureCallbak = new FutureCommandCallback<WebCommand>(callback,session,command);
		commandExecutor.execute(futureCallbak);
	}
	
	class FutureCommandCallback<V> extends FutureTask<V> {

		private Session session;
		private WebCommandCallback callback;
		private WebCommand command;
		
		public FutureCommandCallback(WebCommandCallback callback, Session session, Callable<V> callable) {
			super(callable);
			this.session = session;
			this.callback = callback;
			this.command = ((FutureWebCommandDecorator)callable).getCommand();
		}
		
		public void done() {
			try {
				this.command = (WebCommand)get();
			} catch (Exception e) {
				this.command.setMessage(e.getMessage());
				this.command.setStatus(false);
			}

			this.callback.onReady(session,command);
		}
	}
}
