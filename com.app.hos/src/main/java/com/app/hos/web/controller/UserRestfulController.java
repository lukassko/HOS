package com.app.hos.web.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.hos.pojo.User;
import com.app.hos.security.detailservice.HosUserDetails;
import com.app.hos.security.states.StatesAuthenticator;

@Controller
public class UserRestfulController {
	
	@RequestMapping(value = "/user/getactive", method=RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public ResponseEntity<User> getActiveUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		StatesAuthenticator statesAuthenticator = (StatesAuthenticator)session.getAttribute("authenticator");
		if (statesAuthenticator == null) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		} else {
			ResponseEntity<User> responseUser;
			Optional<Authentication> optionalAuthentication = statesAuthenticator.getAuthentication();
			if (optionalAuthentication.isPresent()) {
				Authentication authentication = optionalAuthentication.get();
				HosUserDetails userDetails = (HosUserDetails)authentication.getPrincipal();
				responseUser = new ResponseEntity<>(new User(userDetails.getUsername()), HttpStatus.OK);
			} else {
				responseUser = new ResponseEntity<>(null, HttpStatus.OK);
			}
			return responseUser;
		}
	}

}
