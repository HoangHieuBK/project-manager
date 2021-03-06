package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.entity.Staff;
import com.example.demo.repository.DepartmentRepo;
import com.example.demo.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	public List<Department> findAllDepartment() {
		// TODO Auto-generated method stub
		return departmentRepo.findAll();
	}

	@Override
	public Optional<Department> findByDepartmentName(String departmentName) {
		return departmentRepo.findByDepartmentName(departmentName);
	}

	@Override
	public Optional<Department> findDepartmentById(int id) {
		// TODO Auto-generated method stub
		return departmentRepo.findById(id);
	}

	@Override
	public Department saveDepartment(Department department) {
		if (checkDepartmentExistInDB(department)) {
			return departmentRepo.save(department);
		}
		return null;
	}

	@Override
	public Department updateDepartment(Department department) {
		return departmentRepo.save(department);
	}

	@Override
	public boolean deleteDepartment(int id) {
		departmentRepo.deleteById(id);
		return true;
	}

	@Override
	public boolean getAmoutStaff(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Staff> getListStaffOfDepartment(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkDepartmentExistInDB(Department department) {
		List<Department> listDepartment = departmentRepo.findAll();
		if (listDepartment.isEmpty()) {
			return true;
		}
		for (int i = 0; i < listDepartment.size(); i++) {
			boolean checkExist = (department.getDepartmentId() == listDepartment.get(i).getDepartmentId() || department.getDepartmentName().equals(listDepartment.get(i).getDepartmentName()));
			if ( checkExist && department.isCheck()) {
				System.out.println("department id: "+ department.getDepartmentId() + ", name :" + department.getDepartmentName() + " da ton tai");
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean existByDepartmentName(String department_name) {
		// TODO Auto-generated method stub
		return departmentRepo.existsByDepartmentName(department_name);
	}

}
