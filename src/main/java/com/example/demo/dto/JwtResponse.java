package com.example.demo.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String username;
	private String accountName;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String token, String username, String accountName, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.username = username;
		this.accountName = accountName;
		this.authorities = authorities;
	}


}
