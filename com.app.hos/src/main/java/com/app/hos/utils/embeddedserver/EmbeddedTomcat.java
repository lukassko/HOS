package com.app.hos.utils.embeddedserver;

import java.lang.reflect.Constructor;

import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

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

	public void initInstance () {
		tomcat.setPort(port);
        tomcat.setBaseDir(baseDir);
        tomcat.getHost().setAppBase(baseDir);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.getHost().setAutoDeploy(true);
        context = tomcat.addWebapp(tomcat.getHost(),contextPath , "src/main/webapp");
	}
	
	public void addServlet(Class<? extends HttpServlet> clazz) throws Exception {
		Constructor<?> ctor = clazz.getConstructor();
		HttpServlet servlet = (HttpServlet)ctor.newInstance();
		WebServlet webServlet = clazz.getAnnotation(WebServlet.class);
		String servletName = webServlet.name();
		String [] mapping = webServlet.urlPatterns();
		Tomcat.addServlet(context, servletName , servlet);
		context.addServletMappingDecoded(mapping[0], servletName);
	}
	
	public void addFilter(Class<? extends Filter> clazz) {
		WebFilter webFilter = clazz.getAnnotation(WebFilter.class);
		String filterName = webFilter.filterName();
		String [] mapping = webFilter.urlPatterns();
		FilterDef filterDef = createFilterDef(filterName, clazz.getName());
		FilterMap filterMap = createFilterMap(filterName, mapping[0]);
		context.addFilterDef(filterDef);
		context.addFilterMap(filterMap);
	}
	
	private FilterDef createFilterDef(String filterName,String filterClass) {
	    FilterDef filterDef = new FilterDef();
	    filterDef.setFilterName(filterName);
	    filterDef.setFilterClass(filterClass);
	    return filterDef;
	}	
	
	private FilterMap createFilterMap(String filterName, String urlPattern) {
	    FilterMap filterMap = new FilterMap();
	    filterMap.setFilterName(filterName);
	    filterMap.addURLPattern(urlPattern);
	    return filterMap;
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
