package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.entity.Task;

public interface AccountService {
	List<Account> findAllAccount();

	List<Account> findAccountNotAssignStaff();

	Optional<Account> findAccountByAccountName(String accountName);

	Account saveAccount(Account account);
	
	Account updateAccount(Account account);
	
	boolean deleteAccount(int idAccount);
	
	Optional<Account> getAccountByID(int idAccount);
	
	Role findByRole(int idAccount);

	List<Task> findTaskByUsername(String username);
}
