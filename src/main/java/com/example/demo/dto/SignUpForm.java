package com.example.demo.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpForm {
 
    @NotBlank
    @Size(min = 3, max = 50)
    private String accountName;
 
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
	@NotBlank
    @Size(min = 6, max = 100)
    private String password;
	
    private Set<String> role;
    
    public SignUpForm(@NotBlank @Size(min = 3, max = 50) String accountName,
			@NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(max = 60) @Email String email,
			@NotBlank @Size(min = 6, max = 100) String password) {
		super();
		this.accountName = accountName;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
