package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Department;
@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
	
//	public Department findDepartmentByName(String nameDepartment);
}
