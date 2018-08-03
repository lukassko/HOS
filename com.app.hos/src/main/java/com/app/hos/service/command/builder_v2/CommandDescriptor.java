package com.app.hos.service.command.builder_v2;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.command.type.DeviceType;


@Retention(RUNTIME)
@Target(TYPE)
public @interface CommandDescriptor {
	CommandType type();
	DeviceType [] device();
}