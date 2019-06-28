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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("/")
	public List<Project> listProject() {
		List<Project> listProject = projectService.getListProject();
		return listProject;
	}

	@PostMapping("add")
	public ResponseEntity<Project> addProject(@RequestBody Project project) {
		return new ResponseEntity<>(projectService.saveProject(project), HttpStatus.OK);
	}

	@PutMapping("edit/{id}")
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
			_project.setDiscription(project.getDiscription());
			_project.setProjectState(project.getProjectState());
			_project.setProjectOutput(project.getProjectOutput());
			return new ResponseEntity<>(projectService.saveProject(_project), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Project> deleteProject(@PathVariable int id) {
		System.out.println("Delete project with ID = " + id + "...");
		projectService.deleteProjectById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public Project detailProject(@PathVariable int id) {
		Optional<Project> projectData = projectService.getProjecByiD(id);
		if (projectData.isPresent()) {
			return projectData.get();
		}
		return null;
	}

	@PostMapping("/project/{id}/staff/add/{idStaff}")
	public ResponseEntity<Project> addStaffInproject(@PathVariable int id, @PathVariable int idStaff) {

		projectService.addStaffInProject(id, idStaff);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/project/{id}/staff/{idStaff}/delete")
	public ResponseEntity<Project> deleteStaffInProject(@PathVariable int id, @PathVariable int idStaff) {
		projectService.deleteStaffIdInProject(idStaff, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
