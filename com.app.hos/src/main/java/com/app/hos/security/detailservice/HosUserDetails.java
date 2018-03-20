package com.app.hos.security.detailservice;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.app.hos.persistance.models.User;

@SuppressWarnings("serial")
public class HosUserDetails implements UserDetailsWithHashing {

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
		throw new UnsupportedOperationException("Use combination of hash and salt with UserHashing interface.");
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
		return false;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.setAuthorities(authorities);
	}

	@Override
	public String getHash() {
		return this.user.getHash();
	}

	@Override
	public String getSalt() {
		return this.user.getSalt();
	}

}
