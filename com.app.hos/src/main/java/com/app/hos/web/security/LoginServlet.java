package com.app.hos.web.security;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.app.hos.persistance.models.User;
import com.app.hos.service.authentication.AuthenticationProvider;

@WebServlet("/logging")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private AuthenticationProvider provider;
	
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
		String userName = (String)request.getAttribute("user");
		String userPassword = (String)request.getAttribute("password");

		User user = provider.authenticate(userName,userPassword);
		if (user == null) {

		} else {

		}
		
    }
}
