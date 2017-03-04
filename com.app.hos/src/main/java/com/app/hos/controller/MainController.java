package com.app.hos.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.hos.persistance.models.Device;
import com.app.hos.service.manager.TaskManager;
import com.app.hos.service.webservices.RestClient;
import com.app.hos.service.webservices.rest.MainClass;
import com.app.hos.service.webservices.rest.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainController {
	
	@Autowired
	private RestClient rest;
	
	private TaskManager taskManager;
	
	@Autowired
	public MainController(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
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
		Set<Device> devices = this.taskManager.getConnectedDevices();
		List<String> devicesAsJson = new LinkedList<String>();
		ObjectMapper mapper = new ObjectMapper(); 
		for (Device device : devices) {
			try {
				devicesAsJson.add(mapper.writeValueAsString(device));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("devices", devicesAsJson);
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
