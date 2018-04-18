package com.app.hos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.security.states.StatesAuthenticator;

@Controller
public class MainController {
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String showMainPage(HttpServletRequest request, Model jspModel) {
		HttpSession session = request.getSession();
		UserDetails user = (UserDetails)session.getAttribute("user");
		jspModel.addAttribute("user-name", user.getUsername());
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
