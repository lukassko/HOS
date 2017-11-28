package com.app.hos.service.websocket;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.utils.exceptions.NotExecutableCommand;

@Service
public class WebSocketManager {

	@Autowired
	private WebCommandFactory factory;
	
	private ExecutorService commandExecutor = Executors.newFixedThreadPool(4);

	public void executeCommand(WebCommandCallback callback, Session session, String command) {
//    	WebCommand cmd = null;
//		try {
//			cmd = JsonConverter.getObject(message, WebCommand.class);
//		} catch (JsonParseException | JsonMappingException e) {
//			cmd = factory.getCommand(WebCommandType.BAD_COMMAND_CONVERSION, e.getMessage());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	sendMessage(session,cmd);
	}
	
	class FutureCommandCallback<V> extends FutureTask<V> {

		private Session session;
		
		public FutureCommandCallback(Session session,Callable<V> callable) {
			super(callable);
			this.session = session;
		}

		public void done() {
			WebCommand command = null;;
			try {
				command = (WebCommand)get();
				//webSocketServer.sendMessage(session, command);
			} catch (Exception e) {
//				command = CommandFactory.getCommand(CommandType.EXECUTION_EXCEPTION);
//				command.setResult(new com.app.hos.share.command.result.Message(e.getMessage()));
				e.printStackTrace();
			}
			
		}
	}
}
