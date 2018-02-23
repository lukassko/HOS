package com.app.hos.service.exceptions.handler.instance;

import org.springframework.stereotype.Service;

import com.app.hos.service.exceptions.handler.HOSExceptionHandler;

@Service
public class DefaultExceptionHandler<T extends Throwable> implements HOSExceptionHandler<T> {

	@Override
	public void handle(Throwable throwable, boolean throwRuntimeException) {
		if (throwRuntimeException) {
			if (throwable  instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			} else {
				throw new RuntimeException(throwable);
			}
		}
		
	}

}
