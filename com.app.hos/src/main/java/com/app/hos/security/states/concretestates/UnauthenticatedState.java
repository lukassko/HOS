package com.app.hos.security.states.concretestates;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.hos.security.states.AuthenticationState;
import com.app.hos.security.states.StatesAuthenticator;

public class UnauthenticatedState implements AuthenticationState {
	
	@Override
	public void doAuthentication(StatesAuthenticator authentication,ServletRequest request, 
			ServletResponse response,FilterChain chain) throws IOException, ServletException {
				
		authentication.setAuthentication(null);
		
		//HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		 //get the old session and invalidate if exists
        HttpSession oldSession = httpRequest.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        //generate a new session
        HttpSession session = httpRequest.getSession(true);
        session.setMaxInactiveInterval(30); // 5 minutes 300
		session.setAttribute("authenticator", authentication);
		
		authentication.setState(new AuthenticatingState());
		
		if (isRequestContainUserName(request)) 
			authentication.doAuthentication(httpRequest, response, chain);
		else 
			forwardToLogin(request,response);
	}
	
	private boolean isRequestContainUserName(ServletRequest request) {
		return request.getParameter("user") != null;
	}

	private void forwardToLogin(ServletRequest request,ServletResponse response) throws ServletException, IOException {
		System.out.println("UnauthenticatedState-forwardToLogin");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/security/login.jsp");
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.setStatus(401);
		dispatcher.forward(request, httpResponse); 
	}
}
