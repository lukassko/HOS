package com.app.hos.security.states.concretestates;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;

import com.app.hos.security.states.AuthenticationState;
import com.app.hos.security.states.StatesAuthenticator;

public class AuthenticatedState implements AuthenticationState {
	
	private Authentication authentication;
	
	public AuthenticatedState(Authentication authentication) {
		System.out.println("AuthenticatedState");
		this.authentication = authentication;
	}

	// TODO:
	// what if someone is login and want to go to login page?
	// cannot go to login page when is login! automatically is redirecting to main
	@Override
	public void doAuthentication(StatesAuthenticator authentication,ServletRequest request, 
			ServletResponse response,FilterChain chain) throws ServletException, IOException {
	
		if (isSessionExpired(request)) {
			// redirect to login page, request should go through filter
			// in filter StatesAuthenticator should be created and new HttpSession where StatesAuthenticator will be stored
			forwardToLogin(request,response);
			return;
		}
		
		if (isRequesting(request, "/logout")) {
			destroySession(request);
			forwardToLogin(request,response);
			return;
		}
		
		if (isRequesting(request, "/login")) {
		// automatically is redirecting to main!
		//	return;
		}
		
		// check principal, roles and allowed operation
			// thats why Authentication object in constructor
		chain.doFilter(request, response);
	}

	private void forwardToLogin(ServletRequest request,ServletResponse response) throws ServletException, IOException {
		// use sendRedirect or forward, must be go through filter to  create new session and set UnauthenticatedState
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/users/login.jsp");
		dispatcher.forward(request, response); 
	}

	private boolean isRequesting(ServletRequest request, String requestPath) {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String context = httpRequest.getContextPath();
		String logoutURI =  context + requestPath;
        String userUrl = httpRequest.getRequestURI();
        return logoutURI.equals(userUrl);
	}

	private void destroySession(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession oldSession = httpRequest.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
	}
	
	private boolean isSessionExpired(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession(false);
        if (session == null && httpRequest.getRequestedSessionId() != null && !httpRequest.isRequestedSessionIdValid()) 
        	return true;
        return false;
	}

}
