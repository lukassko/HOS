package com.app.hos.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.hos.persistance.models.User;

@SuppressWarnings("serial")
public class HosUserDetails implements UserDetails {

	private User user;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public HosUserDetails(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.setAuthorities(authorities);
	}

}
