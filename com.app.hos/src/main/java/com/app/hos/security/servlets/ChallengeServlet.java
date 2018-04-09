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

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.app.hos.pojo.UserChallenge;
import com.app.hos.security.detailservice.UserDetails;
import com.app.hos.utils.json.JsonConverter;
import com.app.hos.utils.security.SecurityUtils;

@SuppressWarnings("serial")
@WebServlet(
		name = "ChallengeServlet", 
		urlPatterns = "/challenge"
)
public class ChallengeServlet extends HttpServlet {

	private UserDetailsService userDetailsService;
	
	public ChallengeServlet() {}
	
	@Autowired
	public ChallengeServlet(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext (this);
	}
	
	// change level to public for unit testing
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ChallengeServlet doGet");
	}
	
	// change level to public for unit testing
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ChallengeServlet doPost");
		String userName = (String)request.getParameter("user");
		try {
			// find user
			org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			UserDetails userHashing = (UserDetails)userDetails;
			
			String userSalt = userHashing.getSalt();
			String challenge = SecurityUtils.getRandomAsString();
			
			// store challenge and user in session
			HttpSession session = request.getSession(false);
			session.setAttribute("challenge", challenge);
			session.setAttribute("user", userHashing);
			
			// send back salt and challenge
			UserChallenge user = new UserChallenge().setSalt(userSalt).setChallenge(challenge);
			String json = JsonConverter.getJson(user);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		} catch(UsernameNotFoundException e) {
			response.sendError(401, e.getMessage());
		}
    }

}
