package com.JHU.models;

import lombok.*;

@NoArgsConstructor // Defines no-args default constructor
@ToString // Defines a meaningful toString implementation of this class
public class LoginCredentials {

    public LoginCredentials(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String email;
    private String password;

}
