package com.app.hos.web.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		System.out.println("Request " + request.getRequestURI());
		System.out.println("Is session null? " + (null == request.getSession(false)));
		Enumeration attrs1 =  req.getAttributeNames();
		while(attrs1.hasMoreElements()) {
		    System.out.println("1 "+attrs1.nextElement());
		}
		Enumeration attrs2 =  request.getAttributeNames();
		while(attrs2.hasMoreElements()) {
		    System.out.println(attrs2.nextElement());
		}
		if (isResourceRequest(request) || isLogging(request) || isLogged(request) || isAauthenticating(request)) {
			chain.doFilter(req, res);
		} else {
			response.sendRedirect("login");
		}
	}

	private boolean isResourceRequest (HttpServletRequest request) {
		String requestUrl = request.getRequestURI();
		if (requestUrl.contains("/resources/") || requestUrl.contains("/webjars/"))
			return true;
		return false;
	}
	
	private boolean isLogged(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute("user") != null;
	}
	
	private boolean isLogging(HttpServletRequest request) {
        String loginURI = request.getContextPath() + "/login";
        String user = request.getParameter("user");
		return 	request.getRequestURI().equals(loginURI) && user == null;
	}
	
	private boolean isAauthenticating(HttpServletRequest request) {
        String loginURI = request.getContextPath() + "/logging";
		return 	request.getRequestURI().equals(loginURI);
	}

}
