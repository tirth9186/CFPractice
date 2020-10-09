package com.cffilter.backend.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cffilter.backend.models.Role;
import com.cffilter.backend.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import antlr.collections.List;

public class UserDetailsImpl implements UserDetails {

	private static final long SerialVersionUID=1L;
	
	private long id;
	private String username;
	private String email;
	private String handle;
	
	@JsonIgnore
	private String password;

	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserDetailsImpl(long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities,String handle) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.handle = handle;
	}
	
	public static UserDetailsImpl build(User user) {
		Collection<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> {
					String roleName = role.getName().toString();
					return new SimpleGrantedAuthority(roleName);
							})
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getUser_id(), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities,user.getHandle());
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public String getEmail() {
		return email;
	}	

	public String getHandle() {
		return handle;
	}

	public long getId() {
		return id;
	}
	


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsImpl other = (UserDetailsImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
