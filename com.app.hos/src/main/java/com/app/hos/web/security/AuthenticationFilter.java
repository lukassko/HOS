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
		
		if (isLogged(request) || isLogging(request)) {
			chain.doFilter(req, res);
		} else {
			redirectToLog(req,res);
		}
	}

	private boolean isLogged(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return session != null && session.getAttribute("user") != null;
	}
	
	private boolean isLogging(HttpServletRequest request) {
        String loginURI = request.getContextPath() + "/logging";
        String user = request.getParameter("user");
		return 	request.getRequestURI().equals(loginURI) && user != null;
	}
	
	private void redirectToLog(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/users/login.jsp");  
		rd.include(req, res);  
	}
}
