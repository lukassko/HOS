package com.app.hos.utils.exceptions.handler;

@SuppressWarnings("rawtypes")
public class HOSExceptionHandlerInfo {

	private Throwable excpetion;
	
	private Class exceptionClass;
	
	private HOSExceptionHandler handler;

	public Throwable getExcpetion() {
		return excpetion;
	}

	public void setExcpetion(Throwable excpetion) {
		this.excpetion = excpetion;
	}

	public Class getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(Class exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public HOSExceptionHandler getHandler() {
		return handler;
	}

	public void setHandler(HOSExceptionHandler handler) {
		this.handler = handler;
	}
	
	
}
