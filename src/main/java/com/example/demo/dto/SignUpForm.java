package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
