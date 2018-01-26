package com.app.hos.utils.exceptions.handler.instance;

import com.app.hos.utils.exceptions.WebSocketException;
import com.app.hos.utils.exceptions.handler.ExceptionHandler;
import com.app.hos.utils.exceptions.handler.HOSExceptionHandler;

@ExceptionHandler
public class WebSocketExceptionHandler implements HOSExceptionHandler<WebSocketException>{
	
	@Override
	public void handle(WebSocketException throwable, boolean throwRuntimeException) {
		
	}
}

    