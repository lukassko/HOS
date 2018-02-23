package com.app.hos.service.exceptions.handler.instance;

import com.app.hos.service.exceptions.HistoryConnectionException;
import com.app.hos.service.exceptions.handler.ExceptionHandler;
import com.app.hos.service.exceptions.handler.HOSExceptionHandler;

@ExceptionHandler
public class HistoryConnectionExceptionHandler implements HOSExceptionHandler<HistoryConnectionException>{

	@Override
	public void handle(HistoryConnectionException throwable, boolean throwRuntimeException) {}

}

    