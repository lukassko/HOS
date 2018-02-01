package com.app.hos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.hos.service.managers.DeviceManager;


@Controller
public class MainController {
	
	//private RestClient rest;
//	private DeviceManager deviceManager;
//	private DeviceWebSocket webSocket;
	
	@Autowired
	public MainController(DeviceManager deviceManager) {
//		this.deviceManager = deviceManager;
//		this.webSocket = webSocket;
	}
	
//	@MessageMapping("/command")
//    @SendTo("/topic/device-info")
//    public void onCommandReceive(String command) {
//        webSocket.receiveMessage(command);
//    }
//	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String showMainPage() {
		return "main/main";
	}
	
	@RequestMapping(value = "/weather", method=RequestMethod.GET)
	public String getWeather() {
		return "services/weather";
	}
	
	@RequestMapping(value = "/dashboard", method=RequestMethod.GET)
	public String getDashboard() {
		return "main/dashboard";
	}
	
	@RequestMapping(value = "/general", method=RequestMethod.GET)
	public String getSystem() {
		return "system/general";
	}
	
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public String getDevices(Model model) {
		return "system/devices";
	}
	
	@RequestMapping(value = "/profile", method=RequestMethod.GET)
	public String getProfile() {
		return "users/profile";
	}
	
	@RequestMapping(value = "/users", method=RequestMethod.GET)
	public String getUsers() {
		return "users/users";
	}
	
}
