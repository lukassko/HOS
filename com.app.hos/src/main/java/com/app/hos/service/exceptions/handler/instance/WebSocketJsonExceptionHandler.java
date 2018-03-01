package com.app.hos.service.exceptions.handler.instance;

import javax.websocket.Session;

import com.app.hos.service.exceptions.WebSocketJsonException;
import com.app.hos.service.exceptions.handler.ExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;
import com.app.hos.service.websocket.WebCommandCallback;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.builder.WebCommandFactory;
import com.app.hos.service.websocket.command.type.WebCommandType;


@ExceptionHandler
public class WebSocketJsonExceptionHandler implements HOSExceptionHandler<WebSocketJsonException>{
	
	@Override
	public void handle(WebSocketJsonException throwable, boolean throwRuntimeException) {
		Session session = throwable.getSession();
		WebCommandCallback callback = throwable.getCallback();
		String message = throwable.getMessage();
		WebCommand cmd = WebCommandFactory.getCommand(WebCommandType.JSON_EXCEPTION, message);
		if (session != null && callback != null) {
			callback.onReady(session, cmd);
		}
		
	}

}

    