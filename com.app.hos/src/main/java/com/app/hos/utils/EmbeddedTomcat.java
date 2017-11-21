package com.app.hos.utils;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;

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
	
	public void init () {
		tomcat.setPort(port);
        tomcat.setBaseDir(BASE_DIR);
        tomcat.getHost().setAppBase(BASE_DIR);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.getHost().setAutoDeploy(true);
        tomcat.addWebapp(tomcat.getHost(), "/" + appName, "src/main/webapp");
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
