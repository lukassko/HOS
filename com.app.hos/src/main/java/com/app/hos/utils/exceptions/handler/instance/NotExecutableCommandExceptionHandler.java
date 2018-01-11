package com.app.hos.utils.exceptions.handler.instance;

import com.app.hos.utils.exceptions.NotExecutableCommandException;
import com.app.hos.utils.exceptions.handler.ExceptionHandler;
import com.app.hos.utils.exceptions.handler.HOSExceptionHandler;

@ExceptionHandler
public class NotExecutableCommandExceptionHandler implements HOSExceptionHandler<NotExecutableCommandException>{

	@Override
	public void handle(NotExecutableCommandException throwable, boolean throwRuntimeException) {
		// TODO Auto-generated method stub
		
	}

}

    