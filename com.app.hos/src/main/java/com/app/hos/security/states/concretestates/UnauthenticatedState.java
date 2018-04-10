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
		
		System.out.println("UnauthenticatedState");
		
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
        session.setMaxInactiveInterval(300); // 5 minutes
		session.setAttribute("authenticator", authentication);
		
		authentication.setState(new AuthenticatingState());
		
		forwardToLogin(request,response);
		//httpResponse.sendRedirect("login");
	}

	private void forwardToLogin(ServletRequest request,ServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/users/login.jsp");
		dispatcher.forward(request, response); 
	}
}
