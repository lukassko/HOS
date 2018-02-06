package com.app.hos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.hos.pojo.WebDeviceStatusesRequest;
import com.app.hos.service.SystemFacade;
import com.app.hos.share.utils.DateTime;

@Controller
public class MainController {
	
	@Autowired
	private SystemFacade systemFacade;
	
	// use HTTP request (not by web socket)
	@RequestMapping(value = "/devices/{serial}", method=RequestMethod.GET)
	public String getDeviceStatuses(@PathVariable(value="serial") String serial,
									@RequestParam("from") DateTime from,
									@RequestParam("to") DateTime to) {
		
		WebDeviceStatusesRequest statusRequet = new WebDeviceStatusesRequest();
		statusRequet.setFrom(from);
		statusRequet.setTo(to);
		statusRequet.setSerial(serial);
		return null;
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
