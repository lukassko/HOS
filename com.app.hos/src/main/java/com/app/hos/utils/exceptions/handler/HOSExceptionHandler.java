package com.app.hos.utils.exceptions.handler;

public interface HOSExceptionHandler <T extends Throwable> {

	public void handle(T throwable, boolean throwRuntimeException);
}
