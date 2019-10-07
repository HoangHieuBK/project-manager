package com.example.demo.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountPrinciple implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String accountName;
	
	private String username;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public AccountPrinciple(Integer id, String accountName, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.accountName = accountName;
        this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	
	public static AccountPrinciple build(Account account) {
		List<GrantedAuthority> authorities = account.getRoles().stream().map(role -> 
		new SimpleGrantedAuthority(role.getRoleName().name())
		).collect(Collectors.toList());
		
		return new AccountPrinciple(
				account.getAccountId(),
				account.getAccountName(),
				account.getUsername(),
				account.getEmail(),
				account.getPassword(),
				authorities
				);
	}
	
    public Integer getId() {
        return id;
    }
 
    public String getAccountName() {
        return accountName;
    }
    
    public String getEmail() {
        return email;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj == null || getClass() != obj.getClass()) return false;
      
      AccountPrinciple account = (AccountPrinciple) obj;
      return Objects.equals(id, account.id);
	}

	
}
