package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;

public interface AccountService {
	List<Account> findAllAccount();

	Account findAccountByAccountName(String accountName);

	Account saveAccount(Account account);
	
	boolean deleteAccount(int idAccount);
	
	Account getAccountByID(int idAccount);
	
	Role findByRole(int idAccount);
}
