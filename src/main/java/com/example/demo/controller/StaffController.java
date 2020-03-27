package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.dto.StaffDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Department;
import com.example.demo.entity.Role;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import com.example.demo.service.AccountService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.StaffService;

@CrossOrigin(origins = "*")
@RestController
public class StaffController {

	@Autowired
	private StaffService staffService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProjectService projectService;

	@GetMapping("/staffs")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<StaffDTO> listStaff() {
		List<Staff> listStaff = staffService.findAll();

		List<StaffDTO> listStaffDTO = new ArrayList<StaffDTO>();

		listStaff.forEach(staff -> {
			StaffDTO _staffDTO = new StaffDTO(staff.getStaffId(), staff.getName(), staff.getGender(),
					staff.getPossition(), staff.getSkill(), staff.getTelephone(), staff.getDescription(),
					staff.getDepartmentId().getDepartmentName(), staff.getAccountId().getAccountName());
			listStaffDTO.add(_staffDTO);
		});
		return listStaffDTO;
	}

	@GetMapping(value = "/staffs/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> detail(@PathVariable int id) {
		Staff staff = staffService.findOne(id);
		if (staff != null) {
			StaffDTO staffDTO = new StaffDTO(staff.getStaffId(), staff.getName(), staff.getGender(),
					staff.getPossition(), staff.getSkill(), staff.getTelephone(), staff.getDescription(),
					staff.getDepartmentId().getDepartmentName(), staff.getAccountId().getAccountName());
			staffDTO.setEmail(staff.getAccountId().getEmail());
			staffDTO.setManagerName(staff.getDepartmentId().getManagerName());

			Set<Role> roles = staff.getAccountId().getRoles();
			Set<String> strRoles = new HashSet<String>();
			roles.forEach(role -> {
				switch (role.getRoleName()) {
				case ROLE_ADMIN:
					strRoles.add("ROLE_ADMIN");
					break;
				case ROLE_PM:
					strRoles.add("ROLE_PM");
					break;
				case ROLE_USER:
					strRoles.add("ROLE_USER");
					break;
				default:
					break;
				}
			});

			staffDTO.setRole(strRoles);
			return new ResponseEntity<>(staffDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/staffs/add")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> addStaff(@RequestBody StaffDTO staffDTO) {
		if (staffService.existByStaffName(staffDTO.getName())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Staff is already taken!"), HttpStatus.BAD_REQUEST);
		}

		Staff staff = new Staff(staffDTO.getName(), staffDTO.getGender(), staffDTO.getPossition(), staffDTO.getSkill(),
				staffDTO.getTelephone(), staffDTO.getDescription());

		Optional<Department> objDepart = departmentService.findByDepartmentName(staffDTO.getDepartmentName());
		if(!objDepart.isPresent()){
			return new ResponseEntity<>(new ResponseMessage("Fail! -> Cause: Department not find."), HttpStatus.BAD_REQUEST);
		}
		staff.setDepartmentId(objDepart.get());

		Optional<Account> objAccount = accountService.findAccountByAccountName(staffDTO.getAccountName());
		if(!objAccount.isPresent()) {
			return new ResponseEntity<>(new ResponseMessage("Fail! -> Cause: Account not find."), HttpStatus.BAD_REQUEST);
		}
		staff.setAccountId(objAccount.get());

		staffService.save(staff);
		return new ResponseEntity<>(new ResponseMessage("Create Staff Successfully!"), HttpStatus.OK);
	}

	@PutMapping("/staffs/edit/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> edit(@PathVariable("id") int id, @RequestBody StaffDTO staffDTO) {
		System.out.println("Update staff with ID = " + id + "...");

		Staff staffData = staffService.findOne(id);

		if (staffData != null) {
			staffData.setName(staffDTO.getName());
			staffData.setGender(staffDTO.getGender());
			;
			staffData.setPossition(staffDTO.getPossition());
			staffData.setSkill(staffDTO.getSkill());
			staffData.setTelephone(staffDTO.getTelephone());
			staffData.setDescription(staffDTO.getDescription());

			Optional<Department> objDepart = departmentService.findByDepartmentName(staffDTO.getDepartmentName());
			if(!objDepart.isPresent()){
				return new ResponseEntity<>(new ResponseMessage("Fail! -> Cause: Department not find."), HttpStatus.BAD_REQUEST);
			}
			staffData.setDepartmentId(objDepart.get());

			Optional<Account> objAccount = accountService.findAccountByAccountName(staffDTO.getAccountName());
			if(!objAccount.isPresent()) {
				return new ResponseEntity<>(new ResponseMessage("Fail! -> Cause: Account not find."), HttpStatus.BAD_REQUEST);
			}
			staffData.setAccountId(objAccount.get());

			return new ResponseEntity<>(new ResponseMessage("Edit staff successfully!"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/staffs/delete/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable int id) {
		System.out.println("Delete staff with ID = " + id + "...");
		staffService.delete(id);
		return new ResponseEntity<>(new ResponseMessage("Staff has been deleted!"), HttpStatus.OK);

	}

	@GetMapping(value = "/staffs/{idstaff}/tasks")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<TaskDTO> getTaskOfStaff(@PathVariable int idstaff) {
		List<Task> listTaskOfStaff = staffService.getListTask(idstaff);
		List<TaskDTO> listTaskDTO = new ArrayList<>();

		listTaskOfStaff.forEach(task -> {
			TaskDTO _taskDTO = new TaskDTO(task.getTaskId(), task.getTaskIdparent(), task.getTaskName(),
					task.getNameCreate(), task.getDateCreate(), task.getDateStart(), task.getDeadlineDate(),
					task.getTaskState(), task.getDiscription(), task.getTaskOutput());
			if (task.getStaffId() != null) {
				_taskDTO.setStaffName(task.getStaffId().getName());
				_taskDTO.setStaffId(task.getStaffId().getStaffId());
			}
			listTaskDTO.add(_taskDTO);
		});
		return listTaskDTO;
	}

}
