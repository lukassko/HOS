package com.app.hos.service.exceptions.handler.instance;

import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.exceptions.handler.ExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;

@ExceptionHandler
public class NotExecutableCommandExceptionHandler implements HOSExceptionHandler<NotExecutableCommandException>{

	@Override
	public void handle(NotExecutableCommandException throwable, boolean throwRuntimeException) {
		
	}

}

    