package com.arif.backenddemo.model;

import lombok.Data;

@Data
public class AuthenticationResponse {


	private String token;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String token) {
		this.setToken(token);
	}

}
