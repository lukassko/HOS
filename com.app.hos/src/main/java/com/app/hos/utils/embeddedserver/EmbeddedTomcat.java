package com.app.hos.utils.embeddedserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.servlet.DispatcherServlet;

import com.app.hos.security.filters.AuthenticationFilter;
import com.app.hos.security.servlets.ChallengeServlet;
import com.app.hos.security.servlets.LoginServlet;

public class EmbeddedTomcat {

	private Tomcat tomcat;
	
	private Context context;
	
	private String baseDir = ".";
	
	private int port = 8080;
	
	private String appName = "HOS";
	 
	private final String contextPath = "/" + appName;
	
	public EmbeddedTomcat () {
		this.tomcat = new Tomcat();
	}
	
	public void setPort (int port) {
		this.port = port;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	
	// maybe add some context
	//Context ctx = tomcat.addContext(contextPath, baseDir);
	//ServletContext sc = ctx.getServletContext();
	public void initInstance () {
		tomcat.setPort(port);
        tomcat.setBaseDir(baseDir);
        tomcat.getHost().setAppBase(baseDir);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.getHost().setAutoDeploy(true);
        context = tomcat.addWebapp(tomcat.getHost(),contextPath , "src/main/webapp");
        //context = tomcat.addContext(contextPath, baseDir);
	}

	public void addServlet(Class<? extends HttpServlet> clazz, String mapping) throws Exception {
		Constructor<?> ctor = clazz.getConstructor();
		HttpServlet servlet = (HttpServlet)ctor.newInstance();

		String name = clazz.getSimpleName();
		System.out.println(name);
		Tomcat.addServlet(context, name , servlet);
		context.addServletMapping(mapping, name);
		//ServletContext servletContext = context.getServletContext();
		//servletContext.addFilter("test", new AuthenticationFilter());

		//tomcat.addServlet(ctxPath, "LoginServlet", new LoginServlet());
		//tomcat.addServlet(ctxPath, "ChallengeServlet", new ChallengeServlet());
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
