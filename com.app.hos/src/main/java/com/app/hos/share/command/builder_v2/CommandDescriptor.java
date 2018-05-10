package com.app.hos.share.command.builder_v2;

import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.command.type.DeviceType;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(RUNTIME)
@Target(TYPE)
public @interface CommandDescriptor {
	CommandType type();
	DeviceType [] device();
}