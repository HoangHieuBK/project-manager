package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer>{
	Optional<Account> findAccountByUsername(String username);
	Optional<Account> findAccountByAccountName(String accountName);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	@Query(value = "select r from Role r where r.roleId = :roleId")
	Role getRole(@Param("roleId") int roleId);

	@Query("select a from Account a where a.accountId not in (select st.accountId from Staff st)")
	List<Account> findAccountNotAssignStaff();

	@Query("select t from Task t where t.staffId.staffId in (" +
			"select s.staffId from Staff s where s.accountId.accountId in (" +
			"select a.accountId from Account a where a.username = :username))")
	List<Task> findTaskByUserName(String username);
}
