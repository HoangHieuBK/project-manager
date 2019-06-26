package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/")
	public List<Department> getAllDepartment() {
		List<Department> listDepartment = departmentService.findAllDepartment();
		return listDepartment;
	}


	@PostMapping("add")
	public ResponseEntity<Department> add(@RequestBody Department department) {
		return new ResponseEntity<>(departmentService.saveDepartment(department), HttpStatus.OK);

	}
	
	
	@PutMapping("edit/{id}")
	public ResponseEntity<Department> edit(@PathVariable("id") int id, @RequestBody Department department) {

		System.out.println("Update department with ID = " + id + "...");

		Optional<Department> departmentData = departmentService.findDepartmentById(id);

		if (departmentData.isPresent()) {
			Department _department = departmentData.get();
			_department.setDepartmentName(department.getDepartmentName());
			_department.setManagerName(department.getManagerName());
			_department.setDiscription(department.getDiscription());
			return new ResponseEntity<>(departmentService.updateDepartment(_department), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		System.out.println("Delete department with ID = " + id + "...");
		departmentService.deleteDepartment(id);
		return new ResponseEntity<>("department has been deleted!", HttpStatus.OK);
	}


	@GetMapping(value = "/detail/{id}")
	public Department detail(@PathVariable int id) {
		
		Optional<Department> departmentData = departmentService.findDepartmentById(id);
		if (departmentData.isPresent()) {
			Department department = departmentData.get();
			return department;
		} 
		
		return null;
	}

	
}
