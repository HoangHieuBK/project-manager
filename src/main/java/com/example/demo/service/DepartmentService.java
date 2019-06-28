package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Department;
import com.example.demo.entity.Staff;

public interface DepartmentService {
	List<Department> findAllDepartment();

	Department findDepartment(String accountName);
	
	Optional<Department> findDepartmentById(int id);
	
	Department saveDepartment(Department department);
	
	Department updateDepartment(Department department);

	boolean deleteDepartment(int id);
	
	boolean getAmoutStaff(int id);
	
	List<Staff> getListStaffOfDepartment(int id);

}