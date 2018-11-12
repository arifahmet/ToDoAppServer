package com.arif.backenddemo.model;

import lombok.Data;

@Data
public class AuthenticationRequest {

	private String username;
	private String password;

	public AuthenticationRequest() {
		super();
	}

	public AuthenticationRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}
}
