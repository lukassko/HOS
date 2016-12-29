package com.app.hos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.hos.service.integration.server.Gateway;

@Controller
public class TestController {

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
		gateway.send("Working!");
	}
}
