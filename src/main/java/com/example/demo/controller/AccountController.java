package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.dto.SignUpForm;
import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.RoleRepo;
import com.example.demo.service.AccountService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	public JavaMailSender emailSender;

	// get list account
	@GetMapping("/accounts")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<Account> getAllAccount() {
		List<Account> listAccount = accountService.findAllAccount();
		return listAccount;
	}

	// get 1 account chi tiet
	@GetMapping("/accounts/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAccountById(@PathVariable int id) {
		Optional<Account> accountData = accountService.getAccountByID(id);
		if(accountData.isPresent()) {
			Account account = accountData.get();
			SignUpForm signUpResponse = new SignUpForm(account.getAccountName(), account.getUsername(), account.getEmail(), account.getPassword());
			Set<Role> roles = account.getRoles();
			Set<String> strRoles = new HashSet<String>();
			
			roles.forEach(role -> {
				switch (role.getRoleName()) {
				case ROLE_ADMIN:
					strRoles.add("admin");
					break;
				case ROLE_PM: 
					strRoles.add("pm");
					break;
				case ROLE_USER:
					strRoles.add("user");
					break;
				default:
					break;
				}
			});
			
			signUpResponse.setRole(strRoles);
			return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(new ResponseMessage("Account not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// them 1 account
	@PostMapping("/accounts/add")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> add(@Valid @RequestBody SignUpForm signUpRequest) {
		if (accountRepo.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (accountRepo.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		Account account = new Account(signUpRequest.getAccountName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepo.findByRoleName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find."));
				roles.add(adminRole);
				break;
			case "pm":
				Role pmRole = roleRepo.findByRoleName(RoleName.ROLE_PM)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find"));
				roles.add(pmRole);
				break;
			default:
				Role staffRole = roleRepo.findByRoleName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find"));
				roles.add(staffRole);
			}
		});

		account.setRoles(roles);
		accountRepo.save(account);

		return new ResponseEntity<>(new ResponseMessage("Create account successfully!"), HttpStatus.OK);
	}

	// edit 1 account
	@PutMapping("/accounts/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> edit(@PathVariable("id") int id, @Valid @RequestBody SignUpForm signUpRequest) {

		System.out.println("Update Account with ID = " + id + "...");

		Optional<Account> accountData = accountService.getAccountByID(id);

		if (accountData.isPresent()) {
			Account _account = accountData.get();

			if (!_account.getPassword().equals(signUpRequest.getPassword())) {
				_account.setPassword(encoder.encode(signUpRequest.getPassword()));
			}

			Set<String> strRoles = signUpRequest.getRole();
			Set<Role> roles = new HashSet<>();
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepo.findByRoleName(RoleName.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find."));
					roles.add(adminRole);
					break;
				case "pm":
					Role pmRole = roleRepo.findByRoleName(RoleName.ROLE_PM)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find"));
					roles.add(pmRole);
					break;
				default:
					Role staffRole = roleRepo.findByRoleName(RoleName.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Account Role not find"));
					roles.add(staffRole);
				}
			});

			_account.setRoles(roles);
			accountService.updateAccount(_account);

			return new ResponseEntity<>(new ResponseMessage("Edit account successfully!"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseMessage("Account not found!"), HttpStatus.NOT_FOUND);
		}
	}

	// xoa 1 account
	@DeleteMapping("/accounts/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteAccount(@PathVariable("id") int id) {
		System.out.println("Delete account with ID = " + id + "...");
		accountService.deleteAccount(id);
		return new ResponseEntity<>(new ResponseMessage("Account has been deleted!"), HttpStatus.OK);
	}

}
