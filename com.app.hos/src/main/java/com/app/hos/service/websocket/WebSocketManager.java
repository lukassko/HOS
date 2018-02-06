package com.app.hos.service.websocket;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.decorators.FutureWebCommandDecorator;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.utils.exceptions.WebSocketJsonException;
import com.app.hos.utils.converters.CommandConverter;
import com.app.hos.utils.exceptions.NotExecutableCommandException;
import com.app.hos.utils.exceptions.handler.ExceptionUtils;
import com.app.hos.utils.json.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class WebSocketManager {

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
	
	private void executeCommand(WebCommandCallback callback, Session session, WebCommand command) throws NotExecutableCommandException {
		Callable<WebCommand> executableCommand = CommandConverter.getExecutableWebCommand(command);
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

			try {
				this.callback.onReady(session,JsonConverter.getJson(command));
			} catch (JsonProcessingException e) {
				ExceptionUtils.handle(new WebSocketJsonException(session, callback, e));
			} catch (IOException e) {
				ExceptionUtils.handle(e);
			}
		}
	}
}
