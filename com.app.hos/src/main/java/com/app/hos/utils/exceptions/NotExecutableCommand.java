package com.app.hos.utils.exceptions;

import com.app.hos.share.command.type.CommandType;

public class NotExecutableCommand extends Exception {

	private static final long serialVersionUID = 1L;

	public NotExecutableCommand(CommandType type) {
		super("Command is not executable " + type.toString());
	}
}
