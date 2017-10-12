package com.app.hos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.hos.service.integration.server.Gateway;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.HelloCommandBuilder;

@Controller
public class TestController {
	private CommandBuilder commandBuilder = new CommandBuilder();
	private Gateway gateway;
	
	@Autowired
	public TestController(Gateway gateway) {
		this.gateway = gateway;
	}
	
	@RequestMapping(value = "/showTest", method=RequestMethod.GET)
	public String showTestPage() {
		return "test/sendMessageTest";
	}
	
	@RequestMapping(value = "/sendMessage", method=RequestMethod.GET)
	public void sendMessage() {
		System.out.println("SEND MESSAGE");
		commandBuilder.setCommandBuilder(new HelloCommandBuilder());
        commandBuilder.createCommand();
        Command cmd = commandBuilder.getCommand();
		//gateway.send(cmd);
	}
}
