package com.app.hos.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.repository.UserRepository;

@Service
@Transactional
public class AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	
}
