package com.app.hos.security.detailservice;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.hos.persistance.models.Role;
import com.app.hos.persistance.models.User;
import com.app.hos.service.managers.UserManager;

public class HosUserDetailsService implements UserDetailsService {

	@Autowired
	private UserManager userManager;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			User user = userManager.findUserByName(userName);
			HosUserDetails userDetails = new HosUserDetails(user);
			userDetails.setAuthorities(getGrantedAuthorities(user));
			return userDetails;
		} catch (NoResultException e) {
			throw new UsernameNotFoundException(userName);
		}
	}

	
	private Collection<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new LinkedList<>();       
        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getUserRole().toString()));
        }
        return authorities;
    }
}
