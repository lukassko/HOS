package com.app.hos.service.authentication;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.User;
import com.app.hos.persistance.repository.UserRepository;

@Service
@Transactional
public class AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	
	public void registerUser(User user) {
		userRepository.save(user);
	}
	
	public User authenticate(String name, String password) {
		try {
			return userRepository.find(name, password);
		} catch (NoResultException e) {
			// for example - throw exception in later development
			return null;
		}
	}
}
