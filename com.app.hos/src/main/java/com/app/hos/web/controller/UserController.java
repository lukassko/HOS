package com.app.hos.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login() {
		return "users/login";
	}
	
}
