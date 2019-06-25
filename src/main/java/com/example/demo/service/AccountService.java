package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Role;

public interface AccountService {
	List<Account> findAllAccount();

	Account findAccountByAccountName(String accountName);

	Account saveAccount(Account account);
	
	Account updateAccount(Account account);
	
	boolean deleteAccount(int idAccount);
	
	Optional<Account> getAccountByID(int idAccount);
	
	Role findByRole(int idAccount);
}
