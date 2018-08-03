package com.app.hos.service.command.future;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.app.hos.service.command.type.CommandType;

@Retention(RUNTIME)
@Target(TYPE)
public @interface FutureCommand {
	CommandType type();
}