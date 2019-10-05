package com.example.demo.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepo;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountRepo accountRepo;
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findAccountByAccountName(username).orElseThrow(
        		() -> new UsernameNotFoundException("Account Not Found with -> accountName or email: " + username));
		return AccountPrinciple.build(account);
	}

	
}
