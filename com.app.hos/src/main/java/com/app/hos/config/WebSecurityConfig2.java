package com.app.hos.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.WebApplicationInitializer;

public class WebSecurityConfig2 implements WebApplicationInitializer  {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("WOOOOOOOOOORK");
		
	}


}
