package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.entity.Department;
import com.example.demo.entity.Staff;
import com.example.demo.service.DepartmentService;

@CrossOrigin(origins = "*")
@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/departments")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<DepartmentDTO> getAllDepartment() {
		List<Department> listDepartment = departmentService.findAllDepartment();
		
		List<DepartmentDTO> listDepartmentDTO = new ArrayList<>();
		
		listDepartment.forEach(department -> {
			DepartmentDTO departmentDTO = new DepartmentDTO(department.getDepartmentId(), department.getDepartmentName(), department.getManagerName(),
					department.getDiscription());
			listDepartmentDTO.add(departmentDTO);
		});
		return listDepartmentDTO;
	}

	@GetMapping(value = "/departments/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> detail(@PathVariable int id) {

		Optional<Department> departmentData = departmentService.findDepartmentById(id);
		if (departmentData.isPresent()) {
			Department department = departmentData.get();

			return new ResponseEntity<>(department, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/departments/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> add(@RequestBody DepartmentDTO departmentDTO) {
		if (departmentService.existByDepartmentName(departmentDTO.getDepartmentName())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> DepartmentName is existed"),
					HttpStatus.BAD_REQUEST);
		}

		Department department = new Department(departmentDTO.getDepartmentName(), departmentDTO.getManagerName(),
				departmentDTO.getDescription());

		Set<Staff> listStaff = new HashSet<>();
		department.setStaffCollection(listStaff);

		departmentService.saveDepartment(department);
		return new ResponseEntity<>(new ResponseMessage("Create department successfully!"), HttpStatus.OK);

	}

	@PutMapping("/departments/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> edit(@PathVariable("id") int id, @RequestBody DepartmentDTO departmentDTO) {

		System.out.println("Update department with ID = " + id + "...");

		Optional<Department> departmentData = departmentService.findDepartmentById(id);

		if (departmentData.isPresent()) {
			Department _department = departmentData.get();
			_department.setDepartmentName(departmentDTO.getDepartmentName());
			_department.setManagerName(departmentDTO.getManagerName());
			_department.setDiscription(departmentDTO.getDescription());
			departmentService.updateDepartment(_department);
			return new ResponseEntity<>(new ResponseMessage("Edit department successfully!"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/departments/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		System.out.println("Delete department with ID = " + id + "...");
		departmentService.deleteDepartment(id);
		return new ResponseEntity<>(new ResponseMessage("Department has been deleted!"), HttpStatus.OK);
	}

}
