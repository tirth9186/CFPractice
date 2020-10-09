package com.cffilter.backend.payload.response;

import java.util.List;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private String handle;
	private List<String> roles;
	
	public JwtResponse(String token, Long id, String username, String email, List<String> roles,String handle) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.handle = handle;
	}

	public String getToken() {
		return token;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}
	
}
