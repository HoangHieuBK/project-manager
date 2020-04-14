package com.example.demo.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginForm;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.dto.SignUpForm;
import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.RoleRepo;
import com.example.demo.security.jwt.JwtProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	// dang nhap
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails  = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	// dang ky
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (accountRepo.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (accountRepo.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		Account account = Account.builder()
				.accountName(signUpRequest.getAccountName())
				.username(signUpRequest.getUsername())
				.email(signUpRequest.getEmail())
				.password(encoder.encode(signUpRequest.getPassword()))
				.build();

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
        
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepo.findByRoleName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find."));
				roles.add(adminRole);
				break;
			case "pm": 
				Role pmRole = roleRepo.findByRoleName(RoleName.ROLE_PM).orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find"));
				roles.add(pmRole);
				break;
			default:
				Role staffRole = roleRepo.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find"));
				roles.add(staffRole);
			}
		});
		
		account.setRoles(roles);
		accountRepo.save(account);
		
		return new ResponseEntity<>(new ResponseMessage("Account registered successfully!"), HttpStatus.OK);
	}
}
