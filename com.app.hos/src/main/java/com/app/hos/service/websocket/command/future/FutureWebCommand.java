package com.app.hos.service.websocket.command.future;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.app.hos.service.websocket.command.WebCommandType;

@Retention(RUNTIME)
@Target(TYPE)
public @interface FutureWebCommand {
	WebCommandType type();
}