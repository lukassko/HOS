package com.app.hos.utils.exceptions.handler.instance;

import org.springframework.stereotype.Service;

import com.app.hos.utils.exceptions.handler.HOSExceptionHandler;

@Service
public class DefaultExceptionHandler<T extends Throwable> implements HOSExceptionHandler<T> {

	@Override
	public void handle(T throwable, boolean throwRuntimeException) {
		if (throwRuntimeException) {
			if (throwable  instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			} else {
				throw new RuntimeException(throwable);
			}
		}
	}

}
