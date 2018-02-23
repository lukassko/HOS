package com.app.hos.service.exceptions.handler.instance;

import java.io.IOException;

import com.app.hos.service.exceptions.handler.ExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;

@ExceptionHandler
public class IOExceptionHandler implements HOSExceptionHandler<IOException>{

	@Override
	public void handle(IOException throwable, boolean throwRuntimeException) {}
}

    