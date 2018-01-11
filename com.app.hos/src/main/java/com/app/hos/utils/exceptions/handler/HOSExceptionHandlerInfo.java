package com.app.hos.utils.exceptions.handler;

public class HOSExceptionHandlerInfo {

	private Throwable excpetion;
	
	private Class<? extends Throwable> exceptionClass;
	
	private HOSExceptionHandler<? extends Throwable> handler;

	public Throwable getExcpetion() {
		return excpetion;
	}

	public void setExcpetion(Throwable excpetion) {
		this.excpetion = excpetion;
	}

	public Class<? extends Throwable> getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(Class<? extends Throwable> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public HOSExceptionHandler<? extends Throwable> getHandler() {
		return handler;
	}

	public void setHandler(HOSExceptionHandler<? extends Throwable> handler) {
		this.handler = handler;
	}
	
	
}
