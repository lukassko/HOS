package com.app.hos.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.app.hos.security.servlets.LoginServlet;

//@Configuration
//@Profile("integration-test")
//@ImportResource("classpath:/web/web-config.xml")
public class WebSecurityConfig  {
	
	//@Bean
	public ServletRegistrationBean loggingServletBean(AuthenticationProvider authenticationProvider) {
	    ServletRegistrationBean bean = new ServletRegistrationBean(
	      new LoginServlet(authenticationProvider), "/login");
	    bean.setLoadOnStartup(1);
	    return bean;
	}

}
