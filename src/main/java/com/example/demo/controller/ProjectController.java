package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.dto.StaffDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskService;

@CrossOrigin(origins = "*")
@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	TaskService taskService;

	@GetMapping("/projects")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<Project> listProject() {
		List<Project> listProject = projectService.getListProject();
		return listProject;
	}

	@RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> detailProject(@PathVariable int id) {
		Optional<Project> projectData = projectService.getProjecByiD(id);
		if (projectData.isPresent()) {
			Project project = projectData.get();
			ProjectDTO projectDTO = new ProjectDTO(project.getProjectId(), project.getProjectName(),
					project.getCreateDate(), project.getStartDate(), project.getDeadlineDate(), project.getFinishDate(),
					project.getDescription(), project.getProjectState(), project.getProjectOutput());
			int numOfStaff = project.getStaffProject().size();
			projectDTO.setNumberOfStaff(numOfStaff);
			int numOfTask = project.getTask().size();
			projectDTO.setNumberOfTask(numOfTask);

			return new ResponseEntity<>(projectDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/projects/add")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO) {
		if (projectService.existByProjectName(projectDTO.getProjectName())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Project is already existed!"),
					HttpStatus.BAD_REQUEST);
		}

		Project project = new Project(projectDTO.getProjectName(), projectDTO.getCreateDate(),
				projectDTO.getStartDate(), projectDTO.getDeadlineDate(), projectDTO.getDescription(),
				projectDTO.getProjectOutput());

		projectService.saveProject(project);
		return new ResponseEntity<>(new ResponseMessage("Create Project Successfully!"), HttpStatus.OK);
	}

	@PutMapping("/projects/edit/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> editProject(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO) {

		System.out.println("Update project with ID = " + id + "...");

		Optional<Project> projectData = projectService.getProjecByiD(id);

		if (projectData.isPresent()) {
			Project _project = projectData.get();
			_project.setProjectName(projectDTO.getProjectName());
			_project.setCreateDate(projectDTO.getCreateDate());
			_project.setStartDate(projectDTO.getStartDate());
			_project.setDeadlineDate(projectDTO.getDeadlineDate());
			_project.setDescription(projectDTO.getDescription());
			_project.setProjectOutput(projectDTO.getProjectOutput());

			projectService.saveProject(_project);

			return new ResponseEntity<>(new ResponseMessage("Edit project successfully!"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/projects/delete/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> deleteProject(@PathVariable int id) {
		System.out.println("Delete project with ID = " + id + "...");
		projectService.deleteProjectById(id);
		return new ResponseEntity<>("Project has been deleted!", HttpStatus.OK);
	}

	@GetMapping(value = "/projects/{id}/staffs")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<StaffDTO> getstaffInProject(@PathVariable int id) {
		List<Staff> listStaffOfProject = projectService.getListStaffOfProject(id);

		List<StaffDTO> listStaffDTO = new ArrayList<StaffDTO>();

		listStaffOfProject.forEach(staff -> {
			StaffDTO _staffDTO = new StaffDTO(staff.getStaffId(), staff.getName(), staff.getGender(),
					staff.getPossition(), staff.getSkill(), staff.getTelephone(), staff.getDescription(),
					staff.getDepartmentId().getDepartmentName(), staff.getAccountId().getAccountName());
			listStaffDTO.add(_staffDTO);
		});
		return listStaffDTO;
	}

	@GetMapping(value = "/projects/{id}/tasks")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<Task> getTasksInProject(@PathVariable int id) {
		List<Task> listTaskOfProject = projectService.getListTaskOfProject(id);
		return listTaskOfProject;
	}

	@GetMapping(value = "/projects/{id}/previousTasks")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<Task> getPreviousTaskInProject(@PathVariable int id) {
		List<Task> listPreviousTaskOfProject = projectService.getListBigTaskOfProject(id);
		return listPreviousTaskOfProject;
	}

	@PostMapping(value = "projects/{id}/addTask")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> addTaskToProject(@PathVariable("id") int id, @RequestBody TaskDTO taskDTO) {
		Task task = new Task(taskDTO.getTaskName(), taskDTO.getNameCreate(), taskDTO.getDateCreate(),
				taskDTO.getDateStart(), taskDTO.getDeadlineDate(), taskDTO.getDiscription(), taskDTO.getTaskOutput());

		Project project = projectService.getProjecByiD(id).get();
		if (project != null) {
			task.setProjectId(project);
		}

		List<Task> listBigTaskOfProject = projectService.getListBigTaskOfProject(id);

		Set<Task> listPreviousTaskOfProject = new HashSet<Task>(listBigTaskOfProject);
		task.setPreviousTask(listPreviousTaskOfProject);

		taskService.saveTask(task);
		return new ResponseEntity<>(new ResponseMessage("Create Task Successfully!"), HttpStatus.OK);
	}

	@GetMapping(value = "/projects/{id}/staffsNotIn")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<StaffDTO> getstaffNotInProject(@PathVariable int id) {
		List<Staff> listStaffNotInProject = projectService.getListStaffNotInProject(id);
		List<StaffDTO> listStaffDTO = new ArrayList<StaffDTO>();

		listStaffNotInProject.forEach(staff -> {
			StaffDTO _staffDTO = new StaffDTO(staff.getStaffId(), staff.getName(), staff.getGender(),
					staff.getPossition(), staff.getSkill(), staff.getTelephone(), staff.getDescription(),
					staff.getDepartmentId().getDepartmentName(), staff.getAccountId().getAccountName());
			listStaffDTO.add(_staffDTO);
		});
		return listStaffDTO;
	}

	@PostMapping("/projects/{id}/staff/add/{idStaff}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> addStaffInproject(@PathVariable int id, @PathVariable int idStaff) {
		projectService.addStaffInProject(id, idStaff);
		return new ResponseEntity<>("Staff has been added into project!", HttpStatus.OK);
	}

	@GetMapping(value = "/projects/{id}/staff/{idStaff}/delete")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> deleteStaffInProject(@PathVariable int id, @PathVariable int idStaff) {
		projectService.deleteStaffIdInProject(idStaff, id);
		return new ResponseEntity<>("Staff has been deleted from project!", HttpStatus.OK);
	}

}
