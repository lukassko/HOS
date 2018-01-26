package com.app.hos.utils.exceptions.handler.instance;

import com.app.hos.utils.exceptions.HistoryConnectionException;
import com.app.hos.utils.exceptions.handler.ExceptionHandler;
import com.app.hos.utils.exceptions.handler.HOSExceptionHandler;

@ExceptionHandler
public class HistoryConnectionExceptionHandler implements HOSExceptionHandler<HistoryConnectionException>{

	@Override
	public void handle(HistoryConnectionException throwable, boolean throwRuntimeException) {}

}

    