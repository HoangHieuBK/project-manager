package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	public JavaMailSender emailSender;

	@GetMapping("/")
	public List<Account> getAllAccount() {
		List<Account> listAccount = accountService.findAllAccount();
		return listAccount;
	}

	@PostMapping("add")
	public ResponseEntity<Account> add(@RequestBody Account account) {
		return new ResponseEntity<>(accountService.saveAccount(account), HttpStatus.OK);

	}

	@PutMapping("edit/{id}")
	public ResponseEntity<Account> edit(@PathVariable("id") int id, @RequestBody Account account) {

		System.out.println("Update Account with ID = " + id + "...");

		Optional<Account> accountData = accountService.getAccountByID(id);

		if (accountData.isPresent()) {
			Account _account = accountData.get();
			_account.setAccountName(account.getAccountName());
			_account.setPassword(account.getPassword());
//			_account.setRole(account.getRole());
			return new ResponseEntity<>(accountService.updateAccount(_account), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable("id") int id) {
		System.out.println("Delete account with ID = " + id + "...");
		accountService.deleteAccount(id);
		return new ResponseEntity<>("Account has been deleted!", HttpStatus.OK);
	}

}
