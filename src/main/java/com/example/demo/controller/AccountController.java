package com.example.demo.controller;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	// get list account
	@GetMapping("/accounts")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<Account> getAllAccount() {
		List<Account> listAccount = accountService.findAllAccount();
		return listAccount;
	}

	// get list account
	@GetMapping("/accounts/notStaff")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<Account> findAccountNotAssignStaff() {
		List<Account> listAccountNotAssignStaff = new ArrayList<>();
		listAccountNotAssignStaff = accountService.findAccountNotAssignStaff();
		return listAccountNotAssignStaff;
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
			return new ResponseEntity<>(new ResponseMessage("Tài khoản không tồn tại!"), HttpStatus.NOT_FOUND);
		}
	}

	// them 1 account
	@PostMapping("/accounts/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> add(@Valid @RequestBody SignUpForm signUpRequest) {
		if (accountRepo.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Lỗi -> tên đăng nhập đã tồn tại!"),
					HttpStatus.BAD_REQUEST);
		}

		if (accountRepo.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Lỗi -> email đã được sử dụng!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
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
				Role adminRole = roleRepo.findByRoleName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Lỗi! -> Nguyên nhân: Không tìm thấy quyền của tài khoản."));
				roles.add(adminRole);
				break;
			case "pm":
				Role pmRole = roleRepo.findByRoleName(RoleName.ROLE_PM)
						.orElseThrow(() -> new RuntimeException("Lỗi! -> Nguyên nhân: Không tìm thấy quyền của tài khoản"));
				roles.add(pmRole);
				break;
			default:
				Role staffRole = roleRepo.findByRoleName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Lỗi! -> Nguyên nhân: Không tìm thấy quyền của tài khoản"));
				roles.add(staffRole);
			}
		});

		account.setRoles(roles);
		accountRepo.save(account);

		return new ResponseEntity<>(new ResponseMessage("Tạo tài khoản thành công!"), HttpStatus.OK);
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
							.orElseThrow(() -> new RuntimeException("Lỗi! -> Nguyên nhân: Không tìm thấy quyền của tài khoản."));
					roles.add(adminRole);
					break;
				case "pm":
					Role pmRole = roleRepo.findByRoleName(RoleName.ROLE_PM)
							.orElseThrow(() -> new RuntimeException("Lỗi! -> Nguyên nhân: Không tìm thấy quyền của tài khoản."));
					roles.add(pmRole);
					break;
				default:
					Role staffRole = roleRepo.findByRoleName(RoleName.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Lỗi! -> Nguyên nhân: Không tìm thấy quyền của tài khoản."));
					roles.add(staffRole);
				}
			});

			_account.setRoles(roles);
			accountService.updateAccount(_account);

			return new ResponseEntity<>(new ResponseMessage("Sửa tài khoản thành công!"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseMessage("Tài khoản không tồn tại!"), HttpStatus.NOT_FOUND);
		}
	}

	// xoa 1 account
	@DeleteMapping("/accounts/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteAccount(@PathVariable("id") int id) {
		System.out.println("Delete account with ID = " + id + "...");
		accountService.deleteAccount(id);
		return new ResponseEntity<>(new ResponseMessage("Tài khoản đã được xóa!"), HttpStatus.OK);
	}

}
