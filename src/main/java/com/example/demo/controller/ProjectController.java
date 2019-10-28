package com.example.demo.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProjectDTO;
import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("/projects")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<ProjectDTO> listProject() {
		List<Project> listProject = projectService.getListProject();
		List<ProjectDTO> _listProjectDTO = new ArrayList<>();
		listProject.forEach(project -> {
			ProjectDTO _projectDTO = new ProjectDTO(project.getProjectName(), project.getCreateDate(), project.getStartDate(), project.getDeadlineDate(), 
					project.getFinishDate(), project.getDescription(), project.getProjectState(), project.getProjectOutput());
			
			Collection<String> _staffsOfProject = new ArrayList<>();
			project.getStaffProject().forEach(staff -> {
				_staffsOfProject.add(staff.getName());
			});
			_projectDTO.setStaffsOfProject(_staffsOfProject);
			
			Collection<String> _task = new ArrayList<>();
			project.getTask().forEach(task -> {
				_task.add(task.getTaskName());
			});
			_projectDTO.setTask(_task);
			
			_listProjectDTO.add(_projectDTO);
		});
		return _listProjectDTO;
	}

	@PostMapping("/projects/add")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Project> addProject(@RequestBody Project project) {
		return new ResponseEntity<>(projectService.saveProject(project), HttpStatus.OK);
	}

	@PutMapping("/projects/edit/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Project> editProject(@PathVariable("id") int id, @RequestBody Project project) {

		System.out.println("Update project with ID = " + id + "...");

		Optional<Project> projectData = projectService.getProjecByiD(id);

		if (projectData.isPresent()) {
			Project _project = projectData.get();
			_project.setProjectName(project.getProjectName());
			_project.setCreateDate(project.getCreateDate());
			_project.setStartDate(project.getStartDate());
			_project.setDeadlineDate(project.getDeadlineDate());
			_project.setFinishDate(project.getFinishDate()); 
			_project.setDescription(project.getDescription());
			_project.setProjectState(project.getProjectState());
			_project.setProjectOutput(project.getProjectOutput());
			return new ResponseEntity<>(projectService.saveProject(_project), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/projects/delete/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Project> deleteProject(@PathVariable int id) {
		System.out.println("Delete project with ID = " + id + "...");
		projectService.deleteProjectById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/projects/detail/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public Project detailProject(@PathVariable int id) {
		Optional<Project> projectData = projectService.getProjecByiD(id);
		if (projectData.isPresent()) {
			return projectData.get();
		}
		return null;
	}

	@PostMapping("/projects/{id}/staff/add/{idStaff}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Project> addStaffInproject(@PathVariable int id, @PathVariable int idStaff) {

		projectService.addStaffInProject(id, idStaff);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/projects/{id}/staff/{idStaff}/delete")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Project> deleteStaffInProject(@PathVariable int id, @PathVariable int idStaff) {
		projectService.deleteStaffIdInProject(idStaff, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
