package com.example.demo.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import com.example.demo.service.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("/project")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("projects", projectService.getListProject());
		modelAndView.setViewName("listproject");
		return modelAndView;
	}

	@GetMapping("/project/add")
	public ModelAndView add() {
//		staff.setDepartmentId(departmentId);
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("project", new Project());
		modelAndView.setViewName("projectform");
		return modelAndView;
	}

	@GetMapping("/project/{id}/edit")
	public ModelAndView edit(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("project", projectService.getProjecByiD(id));
		modelAndView.setViewName("projectform");
		return modelAndView;
	}

	@RequestMapping(value = "/project/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("project") Project project,RedirectAttributes redirect) {

		projectService.saveProject(project);
		redirect.addFlashAttribute("notification","bạn đã thực hiện thành công !");
		return new ModelAndView("redirect:/project");
	}

	@GetMapping("/project/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		projectService.deleteProjectById(id);
		redirect.addFlashAttribute("successMessage", "Deleted staff successfully!");
		return "redirect:/project";
	}

//	@GetMapping("/project/search")
//	public String search(@RequestParam("key") String key) {
//		List<Project> list = projectService.;
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("staffs", list);
//		return "redirect:/staff";
//	}

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
		task.setProjectId(projectService.getProjecByiD(id));
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
