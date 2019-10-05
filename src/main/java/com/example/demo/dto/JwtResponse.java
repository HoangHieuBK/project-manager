package com.example.demo.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String accountName;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String token, String accountName, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.accountName = accountName;
		this.authorities = authorities;
	}

	public String getToken() {
		return token;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
