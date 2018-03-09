package com.app.hos.security;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.hos.persistance.models.User;
import com.app.hos.persistance.repository.UserRepository;
import com.app.hos.security.model.HosUserDetails;

public class HosUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	public void registerUser(User user) {
		userRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			User user = userRepository.find(userName);
			return new HosUserDetails(user);
		} catch (NoResultException e) {
			throw new UsernameNotFoundException(userName);
		}
	}

}
