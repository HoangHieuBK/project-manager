package com.example.demo.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.RoleRepo;
import com.example.demo.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Account findAccountByAccountName(String accountName) {

		return accountRepo.findAccountByAccountName(accountName);
	}

	@Override
	public Account saveAccount(Account account) {
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
//		Role accountRole = roleRepo.findByRoleName("ADMIN");
		Role accountRole = accountRepo.getRole(account.getRoleId().getRoleId());
		System.out.println(accountRole.getRoleName()+"dung null nhe !");
		account.setRoleId(accountRole);
		if(checkEmailExistInDB(account) ) {
			return accountRepo.save(account);
		}
		return null;
	}

	@Override
	public List<Account> findAllAccount() {
		return accountRepo.findAll();
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
	public Account getAccountByID(int idAccount) {
		// TODO Auto-generated method stub
		return accountRepo.getOne(idAccount);
	}

	@Override
	public Role findByRole(int idAccount) {
		Account account = accountRepo.getOne(idAccount);
		return accountRepo.getRole(idAccount);
	}
	
	public boolean checkEmailExistInDB(Account account) {
		List<Account> listAccount = accountRepo.findAll();
		if(listAccount.isEmpty()) {
			return true;
		}
		for(int i=0; i<listAccount.size();i++) {
			if (account.getAccountName().equals(listAccount.get(i).getAccountName()) && account.isCheck()) {
				System.out.println("account name :"+ account.getAccountName() +"da ton tai");
				return false;
			}
		}
		return true;
	}

}
