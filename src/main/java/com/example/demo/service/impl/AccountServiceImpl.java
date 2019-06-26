package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.repository.AccountRepo;
import com.example.demo.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepo accountRepo;

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Account findAccountByAccountName(String accountName) {

		return accountRepo.findAccountByAccountName(accountName);
	}

	@Override
	public Account saveAccount(Account account) {
		account.setPassword(account.getPassword());
//		Role accountRole = roleRepo.findByRoleName("ADMIN");
		Role accountRole = accountRepo.getRole(account.getRole().getRoleId());
		System.out.println(accountRole.getRoleName() + "dung null nhe !");
		account.setRole(accountRole);
		if (checkEmailExistInDB(account)) {
			return accountRepo.save(account);
		}
		return null;
	}

	@Override
	public Account updateAccount(Account account) {
			return accountRepo.save(account);
	}

	@Override
	public List<Account> findAllAccount() {
		List<Account> listAccount = accountRepo.findAll();

		return listAccount;
	}

	@Override
	public boolean deleteAccount(int idAccount) {
		Account account = accountRepo.getOne(idAccount);
		if (account.equals(null) || account == null) {
			return false;
		}
		accountRepo.deleteById(idAccount);
		return true;
	}

	@Override
	public Optional<Account> getAccountByID(int idAccount) {
		// TODO Auto-generated method stub
		return accountRepo.findById(idAccount);
	}

	@Override
	public Role findByRole(int idAccount) {
//		Account account = accountRepo.getOne(idAccount);
		return accountRepo.getRole(idAccount);
	}

	public boolean checkEmailExistInDB(Account account) {
		List<Account> listAccount = accountRepo.findAll();
		if (listAccount.isEmpty()) {
			return true;
		}
		for (int i = 0; i < listAccount.size(); i++) {
			boolean checkExist = (account.getAccountId() == listAccount.get(i).getAccountId() || account.getAccountName().equals(listAccount.get(i).getAccountName()));
			if ( checkExist && account.isCheck()) {
				System.out.println("account name :" + account.getAccountName() + "da ton tai");
				return false;
			}
		}
		return true;
	}

}
