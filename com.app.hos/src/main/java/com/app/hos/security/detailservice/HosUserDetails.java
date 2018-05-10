package com.app.hos.security.detailservice;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.app.hos.persistance.models.user.User;

@SuppressWarnings("serial")
public class HosUserDetails implements UserDetails {

	private User user;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public HosUserDetails(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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
		this.authorities = authorities;
	}

	@Override
	public String getHash() {
		return this.user.getHash();
	}

	@Override
	public String getSalt() {
		return this.user.getSalt();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HosUserDetails other = (HosUserDetails) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HosUserDetails [user=" + user + "]";
	}


}
