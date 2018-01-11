package com.app.hos.tests.utils;

import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.exceptions.NotExecutableCommandException;

public class Thrower {
	
	public void throwNotExeccutableCommand () {
		throw new NotExecutableCommandException(CommandType.UNKNOWN_COMMAND);
	}

}
