package com.example.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskProgress;
import com.example.demo.service.ProjectService;
import com.example.demo.service.StaffService;
import com.example.demo.service.TaskProgressService;
import com.example.demo.service.TaskService;

@Controller
public class TaskController {
	@Autowired
	TaskService taskService;

	@Autowired
	StaffService staffService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskProgressService taskProgressService;

	@RequestMapping(value = "/project/{id}/task/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("task") Task task, @PathVariable("id") int id,RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
//		String nameAssign = staffService.findOne(task.getStaffId().getStaffId()).getFullName();
//		task.setNameAssign(nameAssign);
		taskService.saveTask(task);
		modelAndView.addObject("project", projectService.getProjecByiD(id));
		modelAndView.setViewName("redirect:/project/{id}/task");
		redirect.addFlashAttribute("notification","bạn thực hiện action thành công !");
		return modelAndView;
	}

	//edit task
	@GetMapping(value = "project/{idproject}/task/{id}/edit")
	public ModelAndView edit(@PathVariable("idproject") int idproject, @PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);

		Task task = taskService.findById(id);
		modelAndView.addObject("task", task);
		List<Staff> listStaff = projectService.getListStaffOfProject(task.getProjectId().getProjectId());
		Map<Integer, String> staffs = new HashMap<>();
		listStaff.forEach(item -> staffs.put(item.getStaffId(), item.getFullName()));
		modelAndView.addObject("staffs", staffs);
		modelAndView.addObject("project", projectService.getProjecByiD(idproject));
		modelAndView.setViewName("taskform");
		return modelAndView;
	}

	@GetMapping(value = "project/{id}/staff/{idstaff}/task/add")
	public ModelAndView addTaskToStaff(@PathVariable("id") int id, @PathVariable("idstaff") int idstaff) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);

		Task task = new Task();
		task.setProjectId(projectService.getProjecByiD(id));
		modelAndView.addObject("task", task);
		Map<Integer, String> staffs = new HashMap<>();
		Staff staff = staffService.findOne(idstaff);
		staffs.put(staff.getStaffId(), staff.getFullName());
		modelAndView.addObject("staffs", staffs);
		modelAndView.setViewName("taskform");
		return modelAndView;
	}

	@GetMapping(value = "project/{idproject}/task/{id}/addsubtask")
	public ModelAndView addSubTask(@PathVariable("idproject") int idproject, @PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);

		modelAndView.addObject("project", projectService.getProjecByiD(idproject));

		Task parentTask = taskService.findById(id);
		Task task = new Task();
		task.setProjectId(parentTask.getProjectId());
		task.setTaskIdparent(parentTask.getTaskId());
		modelAndView.addObject("task", task);
		List<Staff> listStaff = projectService.getListStaffOfProject(task.getProjectId().getProjectId());
		Map<Integer, String> staffs = new HashMap<>();
		listStaff.forEach(item -> staffs.put(item.getStaffId(), item.getFullName()));
		modelAndView.addObject("staffs", staffs);
		modelAndView.setViewName("taskform");
		return modelAndView;
	}

	@GetMapping(value = "/task/detail/{id}")
	public ModelAndView getTask(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);

		modelAndView.addObject("task", taskService.findById(id));
		modelAndView.setViewName("detailtask");
		return modelAndView;
	}

	@GetMapping(value = "project/{id}/task/delete/{idtask}")
	public ModelAndView deleteTask(@PathVariable("id") int id, @PathVariable("idtask") int idtask,
			RedirectAttributes redirect) {
		ModelAndView modeleAndView = new ModelAndView();
		modeleAndView.setViewName("redirect:/project/{id}/task");
		List<Task> listTaskOfProject = projectService.getListTaskOfProject(id);
		Task task = taskService.findById(idtask);
		for (int i = 0; i < listTaskOfProject.size(); i++) {
			if (task.getTaskId() == listTaskOfProject.get(i).getTaskIdparent()) {
				System.out.println("task nay dang co task con nen ban phai xoa task con truoc !");
				redirect.addFlashAttribute("notification",
						"Task " + task.getTaskName() + " có task con, Nếu muốn xóa bạn phải xóa task con trước !");
				return modeleAndView;
			}
		}
		taskService.deleteTask(idtask);
		redirect.addFlashAttribute("notification","Thực hiện xóa task thành công !");
		return modeleAndView;
	}


	@GetMapping(value = "task/{id}/worklog")
	public ModelAndView createWorkLog(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		TaskProgress taskProgress = new TaskProgress();
		Task task = taskService.findById(id);
		Set<Task> previousTasks = task.getPreviousTask();
		for (Task previousTask : previousTasks) {
			if (previousTask.getTaskState() < 100) {
				mav.setViewName("/error/403");
				return mav;
			}
		}
		taskProgress.setTaskId(task);
		taskProgress.setDateLog(new Date());
		mav.addObject("taskprogress", taskProgress);
		mav.addObject(task);
		mav.setViewName("progresstaskform");
		return mav;
	}

	//assign task
	@GetMapping(value = "project/{projectId}/task/{taskId}/assign")
	public ModelAndView assignTask(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId,RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
		
		Task task = taskService.findById(taskId);
		modelAndView.addObject("task", task);
		List<Staff> listStaff = projectService.getListStaffOfProject(task.getProjectId().getProjectId());
		Map<Integer, String> staffs = new HashMap<>();
		listStaff.forEach(item -> staffs.put(item.getStaffId(), item.getFullName()));
		modelAndView.addObject("staffs", staffs);
		modelAndView.addObject("project", projectService.getProjecByiD(projectId));
		modelAndView.setViewName("assignTask");
		redirect.addFlashAttribute("message","bạn thực hiện assign task thành công !");
		return modelAndView;
	}
}
