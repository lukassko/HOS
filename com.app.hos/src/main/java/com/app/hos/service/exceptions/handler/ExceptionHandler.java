package com.app.hos.service.exceptions.handler;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Retention(RUNTIME)
@Target(TYPE)
@Component
public @interface ExceptionHandler {
	
}