package com.app.hos.service.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.User;
import com.app.hos.persistance.repository.UserRepository;

@Service
@Transactional
public class UserManager {

	@Autowired
	private UserRepository userRepository;
	
	public User findUserByName(String userName) {
		return userRepository.findByName(userName);
	}
	
	public void addUser(User user) {
		userRepository.save(user);
	}
}
