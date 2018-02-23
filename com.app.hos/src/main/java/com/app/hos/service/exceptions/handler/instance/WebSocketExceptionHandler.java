package com.app.hos.service.exceptions.handler.instance;

import com.app.hos.service.exceptions.WebSocketException;
import com.app.hos.service.exceptions.handler.ExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;

@ExceptionHandler
public class WebSocketExceptionHandler implements HOSExceptionHandler<WebSocketException>{
	
	@Override
	public void handle(WebSocketException throwable, boolean throwRuntimeException) {
		
	}
}

    