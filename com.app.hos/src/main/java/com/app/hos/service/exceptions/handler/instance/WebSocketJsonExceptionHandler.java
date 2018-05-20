package com.app.hos.service.exceptions.handler.instance;

import javax.websocket.Session;

import com.app.hos.service.exceptions.WebSocketJsonException;
import com.app.hos.service.exceptions.handler.ExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;
import com.app.hos.service.websocket.WebCommandCallback;
import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.WebCommandFactory;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;

@ExceptionHandler
public class WebSocketJsonExceptionHandler implements HOSExceptionHandler<WebSocketJsonException>{
	
	@Override
	public void handle(WebSocketJsonException throwable, boolean throwRuntimeException) {
		Session session = throwable.getSession();
		WebCommandCallback callback = throwable.getCallback();
		String message = throwable.getMessage();
		WebCommand cmd = WebCommandFactory.get(WebCommandType.JSON_EXCEPTION, message);
		if (session != null && callback != null) {
			callback.onReady(session, cmd);
		}
		if (throwRuntimeException) {
			if (throwable instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			} else {
				throw new RuntimeException(throwable);
			}
		}
	}

}

    