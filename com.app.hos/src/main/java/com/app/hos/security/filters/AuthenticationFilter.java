package com.app.hos.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.app.hos.security.states.StatesAuthenticator;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		StatesAuthenticator statesAuthenticator = getStatesAuthenticator(request);
		statesAuthenticator.doAuthentication(request, response, chain);
	}

	private StatesAuthenticator getStatesAuthenticator(ServletRequest req) {
		StatesAuthenticator statesAuthenticator;
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession(false);
		if (session == null) 
			statesAuthenticator = new StatesAuthenticator();
		else 
			statesAuthenticator = (StatesAuthenticator)session.getAttribute("authenticator");
		
		return statesAuthenticator;
	}
	
	/*private boolean isResourceRequest (HttpServletRequest request) {
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
	}*/

}
