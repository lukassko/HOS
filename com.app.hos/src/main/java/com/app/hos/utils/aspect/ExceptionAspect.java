package com.app.hos.utils.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.app.hos.logging.repository.LoggingRepository;

@Aspect
public class ExceptionAspect extends Logger {

	public ExceptionAspect(LoggingRepository repository) {
		super(repository);
	}

	@Pointcut("execution(* com.app.hos.utils.exceptions.handler.HOSExceptionHandler.handle(..)) && args(throwable,throwRuntimeException)")
	public <T extends Throwable> void excpetionHandlerPointcut(T throwable, boolean throwRuntimeException) {}
	
	@Before("excpetionHandlerPointcut(throwable,throwRuntimeException)")
	public <T extends Throwable> void excpetionHandler(JoinPoint point,T throwable, boolean throwRuntimeException) {

	}
}
