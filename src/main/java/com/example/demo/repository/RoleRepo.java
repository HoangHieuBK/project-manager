package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(RoleName roleName);

}
