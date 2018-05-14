package com.app.hos.service.exceptions.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionUtils {

	private static HOSExceptionHandlerFactory exceptionHandlerFactory;
	
	@Autowired
	public void setExceptionHandlerFactory(HOSExceptionHandlerFactory exceptionHandlerFactory) {
		ExceptionUtils.exceptionHandlerFactory = exceptionHandlerFactory;
	}

	public static void handle(final Throwable throwable) {
		ExceptionUtils.handle(throwable, true);
	}
	
	@SuppressWarnings("unchecked")
	public static void handle(final Throwable throwable, final boolean throwRuntimeException) {
		HOSExceptionHandlerInfo info = exceptionHandlerFactory.get(throwable);
		if (info != null) {
			info.getHandler().handle(throwable, throwRuntimeException);
		} else {
			exceptionHandlerFactory.getDefaultExceptionHandler().handle(throwable, throwRuntimeException);
		}
	}
}
