package com.app.hos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.hos.service.webservices.RestClient;
import com.app.hos.service.webservices.rest.MainClass;

@Controller
public class MainController {
	
	@Autowired
	RestClient rest;
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String showMainPage() {
		return "main/main";
	}
	
	@RequestMapping(value = "/weather", method=RequestMethod.GET)
	public String getWeather() {
		rest.getData("Krakow");
		return "services/weather";
	}
}
