package com.app.hos.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.hos.persistance.models.Device;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.utils.json.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class MainController {
	
	//private RestClient rest;
	private DeviceManager deviceManager;
	
	@Autowired
	public MainController(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}
	
	// TESTING WESOCKETS
	
	@MessageMapping("/device-broker")
    @SendTo("/topic/device-info")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new String("NO_SIEMA");
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
		Set<Device> devices = this.deviceManager.getConnectedDevices();
		String jsonDevices = JsonConverter.getJson(devices);
		model.addAttribute("devices", jsonDevices);
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
