package com.app.hos.security.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.app.hos.pojo.UserHash;
import com.app.hos.security.UserHashing;
import com.app.hos.security.model.HosUserAuthentication;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.security.states.concretestates.AuthenticatedState;
import com.app.hos.security.states.concretestates.AuthenticatingState;
import com.app.hos.utils.security.SecurityUtils;

@SuppressWarnings("serial")
@WebServlet("/logging")
public class LoginServlet extends HttpServlet {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext (this);
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	// user try to log in
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hash = (String)request.getAttribute("hash");
		
		HttpSession session = SecurityUtils.getSessionFromHttpServletRequest(request);
		
		StatesAuthenticator statesAuthenticator = (StatesAuthenticator)session.getAttribute("authenticator");
		UserHashing userHashing = (UserHashing)session.getAttribute("user");
		String challenge = (String)session.getAttribute("challenge");
		
		UserHash userHash = new UserHash().setHash(hash).setSalt(userHashing.getSalt()).setChallenge(challenge);
		
		try {

			Authentication authentication = new HosUserAuthentication(userHashing).setCredentials(userHash);
			
			authentication = authenticationProvider.authenticate(authentication);	
			statesAuthenticator.setState(new AuthenticatedState(authentication));
			response.sendRedirect("/");

		} catch (AuthenticationException e) {
			statesAuthenticator.setState(new AuthenticatingState());
			SecurityUtils.unauthorized(response, e.getMessage());
		}
    }

}
