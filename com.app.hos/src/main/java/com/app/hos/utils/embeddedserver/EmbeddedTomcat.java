package com.app.hos.utils.embeddedserver;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;

import com.app.hos.security.servlets.ChallengeServlet;
import com.app.hos.security.servlets.LoginServlet;

public class EmbeddedTomcat {

	private Tomcat tomcat;
	
	private final String BASE_DIR = ".";
	
	private int port = 8080;
	
	private String appName = "HOS";
	 
	private final String ctxPath = "/" + appName;
	
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
        tomcat.addWebapp(tomcat.getHost(),ctxPath , "src/main/webapp");
	}
	
	public void initServletContext() {
		tomcat.addServlet(ctxPath, "LoginServlet", new LoginServlet());
		tomcat.addServlet(ctxPath, "ChallengeServlet", new ChallengeServlet());
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
