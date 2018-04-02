package com.app.hos.utils;

import javax.servlet.ServletContext;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;

import com.app.hos.security.servlets.LoginServlet;

public class EmbeddedTomcat {

	private Tomcat tomcat;
	
	private final String BASE_DIR = ".";
	
	private int port = 8080;
	private String appName = "HOS";
	
	public EmbeddedTomcat () {
		this.tomcat = new Tomcat();
		
	}
	
	public void setPort (int port) {
		this.port = port;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public void initInstance () {
		tomcat.setPort(port);
        tomcat.setBaseDir(BASE_DIR);
        tomcat.getHost().setAppBase(BASE_DIR);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.getHost().setAutoDeploy(true);
        tomcat.addWebapp(tomcat.getHost(), "/" + appName, "src/main/webapp");
        initServletContext();
	}
	
	public void initServletContext() {
		tomcat.addServlet("/" + appName, "login-servlet", "LoginServlet");
	}
	
	public void start () throws LifecycleException {
		tomcat.start();
	}
	
	public void stop () throws LifecycleException {
		tomcat.stop();
		tomcat.destroy();
	}
	
	public boolean isStarted() {
		return LifecycleState.STARTED.equals(tomcat.getServer().getState());
	}
}
