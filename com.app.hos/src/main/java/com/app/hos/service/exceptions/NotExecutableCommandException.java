package com.app.hos.service.exceptions;

import com.app.hos.share.command.type.CommandType;

public class NotExecutableCommandException extends HOSException {

	private static final long serialVersionUID = 1L;

	public NotExecutableCommandException(CommandType type) {
		super("Command is not executable " + type.toString());
	}
}
