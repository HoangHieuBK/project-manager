package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer>{
	Account findAccountByAccountName(String accountName); 
	
	@Query("select r from Role r where r.roleId = :roleId")
	Role getRole(@Param("roleId") int roleId);
}
