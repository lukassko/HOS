package com.app.hos.utils.aspect;

import org.aspectj.lang.annotation.Aspect;

import com.app.hos.logging.repository.LoggingRepository;

@Aspect
public class ExceptionAspect extends Logger {

	public ExceptionAspect(LoggingRepository repository) {
		super(repository);
	}

}
