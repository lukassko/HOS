package com.app.hos.security.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.authentication.HosUserAuthentication;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.security.states.StatesAuthenticator;
import com.app.hos.security.states.concretestates.AuthenticatedState;

@SuppressWarnings("serial")
@WebServlet(
		name = "LoginServlet",
		urlPatterns = {"/login"}
)
public class LoginServlet extends HttpServlet {

	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	public LoginServlet() {}
	
	//@Autowired
	public LoginServlet(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}
	
	@Override
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext (this);
	}
	
	// change level to public for unit testing
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/security/login.jsp");
		dispatcher.forward(request, response); 
	}
	
	// change level to public for unit testing
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String oneTimeChallengeResponse = (String)request.getParameter("challenge");

		HttpSession session = request.getSession(false);

		String challenge = (String)session.getAttribute("challenge");
		UserDetails user = (UserDetails)session.getAttribute("user");
		
		StatesAuthenticator statesAuthenticator = (StatesAuthenticator)session.getAttribute("authenticator");
		
		UserChallenge userHash = new UserChallenge().setHash(user.getHash())
													.setSalt(user.getSalt())
													.setChallenge(challenge)
													.setOneTimeRequest(oneTimeChallengeResponse);
		try {
			Authentication authentication = new HosUserAuthentication(user).setCredentials(userHash);
			authentication = authenticationProvider.authenticate(authentication);	
			statesAuthenticator.setState(new AuthenticatedState(authentication));
			statesAuthenticator.setAuthentication(authentication);
			response.setStatus(200);
			//forwardToMain(request,response);
		} catch (AuthenticationException e) {
			response.sendError(401,e.getMessage());
		}
    }
	
	private void forwardToMain(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/main/main.jsp");
		dispatcher.forward(request, response); 
	}
	
}
