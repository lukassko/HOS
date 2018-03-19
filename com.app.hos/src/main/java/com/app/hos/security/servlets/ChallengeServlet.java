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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.app.hos.pojo.UserHash;
import com.app.hos.security.UserHashing;
import com.app.hos.utils.json.JsonConverter;
import com.app.hos.utils.security.SecurityUtils;


@SuppressWarnings("serial")
@WebServlet("/challenge")
public class ChallengeServlet extends HttpServlet {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext (this);
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = (String)request.getAttribute("user");
		try {
			// find user
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			UserHashing userHashing = (UserHashing)userDetails;
			
			String userSalt = userHashing.getSalt();
			String challenge = SecurityUtils.getRandomSalt().toString();
			
			// store challenge and user in session
			HttpSession session = request.getSession(false);
			session.setAttribute("challenge", challenge);
			session.setAttribute("user", userHashing);
			
			// send back salt and challenge
			UserHash user = new UserHash().setSalt(userSalt).setChallenge(challenge);
			String json = JsonConverter.getJson(user);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		} catch(UsernameNotFoundException e) {
			response.sendError(401, e.getMessage());
		}
    }

}
