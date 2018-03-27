package com.app.hos.security.states.concretestates;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.hos.security.states.AuthenticationState;
import com.app.hos.security.states.StatesAuthenticator;

public class AuthenticatingState implements AuthenticationState {

	private final String errorMessage = "Unauthorized request";
	
	@Override
	public void doAuthentication(StatesAuthenticator authentication,ServletRequest request, 
			ServletResponse response,FilterChain chain) throws IOException, ServletException{
		
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
        if (isChallengeLoggingRequest(request))
        	chain.doFilter(request, response);
        else
        	httpResponse.sendError(401, errorMessage);
        
	}

	private boolean isChallengeLoggingRequest(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String context = httpRequest.getContextPath();
		String loginURI =  context + "/login";
		String challnangeURI =  context + "/challenge";
        String userTryUrl = httpRequest.getRequestURI();
        return userTryUrl.equals(loginURI) || userTryUrl.equals(challnangeURI);
	}
}
