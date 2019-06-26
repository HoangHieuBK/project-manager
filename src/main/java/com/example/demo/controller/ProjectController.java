package com.example.demo.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
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

	@RequestMapping(value = "/project/detail/{id}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable int id) {

		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);

		modelAndView.addObject("project", projectService.getProjecByiD(id));

		modelAndView.setViewName("detailproject");
		return modelAndView;
	}

	@GetMapping(value = "/project/{id}/task")
	public ModelAndView getTask(@PathVariable int id,RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("project", projectService.getProjecByiD(id));
		modelAndView.addObject("tasks", projectService.getListTaskOfProject(id));
		modelAndView.setViewName("listtaskofproject");
		redirect.addFlashAttribute("notification","bạn đã thực hiện thêm task thành công !");
		return modelAndView;

	}

	@GetMapping(value = "/project/{id}/staff")
	public ModelAndView getstaff(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("project", projectService.getProjecByiD(id));
		modelAndView.addObject("staffs", projectService.getListStaffOfProject(id));
		modelAndView.setViewName("liststaffofproject");
		return modelAndView;

	}

	@GetMapping(value = "/project/{id}/staff/add")
	public ModelAndView addStaffProject(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("project", projectService.getProjecByiD(id));
		List<Staff> listStaff = projectService.getListStaffNotInProject(id);
//		if (listStaff.isEmpty()) {
//			modelAndView.setViewName("error/404");
//			return modelAndView;
//		}
		Map<Integer, String> staffs = new HashMap<>();
		listStaff.forEach(item -> staffs.put(item.getStaffId(), item.getFullName()));

		modelAndView.addObject("staffs", staffs);
		for (int i = 0; i < listStaff.size(); i++) {
			modelAndView.addObject("staff", listStaff.get(i));
		}
		modelAndView.setViewName("addstaffinproject");
		return modelAndView;
	}

	@PostMapping(value = "/project/{id}/staff/add/{idStaff}")
	public String addStaffInproject(@PathVariable int id, @PathVariable int idStaff,RedirectAttributes redirect) {
		projectService.addStaffInProject(id, idStaff);
		
		redirect.addFlashAttribute("notification","bạn đã thêm nhân viên thành công !");
		return "redirect:/project/{id}/staff";
	}

	@GetMapping(value = "/project/{id}/addtask")
	public String addTask(@PathVariable("id") int id, Model model,HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		model.addAttribute("username",principal.getName());
		List<Task> taskList = projectService.getListBigTaskOfProject(id);
		List<Staff> listStaff = projectService.getListStaffOfProject(id);
		Map<Integer, String> staffs = new HashMap<>();
		listStaff.forEach(item -> staffs.put(item.getStaffId(), item.getFullName()));
		model.addAttribute("staffs", staffs);
		Map<Integer, String> listPreviousTask = new HashMap<>();
		taskList.forEach(item -> listPreviousTask.put(item.getTaskId(), item.getTaskName()));
		model.addAttribute("listPreviousTask", listPreviousTask);
		Task task = new Task();
		task.setProjectId(projectService.getProjecByiD(id).get());
		model.addAttribute("task", task);
		return "taskform";
	}

	@GetMapping(value = "/project/{id}/staff/{idStaff}/delete")
	public String deleteStaffInProject(@PathVariable int id, @PathVariable int idStaff,RedirectAttributes redirect) {
		projectService.deleteStaffIdInProject(idStaff, id);
		redirect.addFlashAttribute("notification","bạn đã xóa nhân viên thành công !");
		return "redirect:/project/{id}/staff";
	}

//	@GetMapping(value = "/project/{id}/progress")
//	public String detailProject(@PathVariable int id, Model model) {
//		List<WorkLog> workLogList = workLogService.findByProjectIDOrderByDateCreateAsc(id);
//		Project project = projectService.getProjecByiD(id);
//		List<Date> listDate = Util.getListDate(project.getStartDate(), project.getFinishDate(),
//				workLogList.get(workLogList.size()-1).getDateCreate());
//		List<Double> listProgress = Util.getListExpectProgress(project.getStartDate(), project.getFinishDate());
//		List<Double> listActualProgress = Util.getListActualProgress(project.getStartDate(), workLogList);
//		List<String> listLabel = Util.getLabelFromListDate(listDate);
//		model.addAttribute("listProgress", listProgress);
//		model.addAttribute("listActualProgress", listActualProgress);
//		model.addAttribute("listLabel", listLabel);
//		return "progressproject";
//	}
}
