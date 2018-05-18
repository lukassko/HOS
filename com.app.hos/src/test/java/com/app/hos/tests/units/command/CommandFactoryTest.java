package com.app.hos.tests.units.command;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.type.CommandType;

public class CommandFactoryTest {

	@Test
	public void createHelloCommandTest() {
		Command command = CallableCommandFactory.getCommand(CommandType.HELLO);
		Assert.assertEquals(CommandType.HELLO, command.getEnumeratedCommandType());
	}
	
	@Test
	public void createStatusCommandTest() {
		Command command = CallableCommandFactory.getCommand(CommandType.MY_STATUS);
		Assert.assertEquals(CommandType.MY_STATUS, command.getEnumeratedCommandType());
	}
}
