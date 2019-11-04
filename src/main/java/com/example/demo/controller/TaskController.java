package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskProgress;
import com.example.demo.service.ProjectService;
import com.example.demo.service.StaffService;
import com.example.demo.service.TaskProgressService;
import com.example.demo.service.TaskService;

@CrossOrigin(origins = "*")
@RestController
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
	public ModelAndView save(@ModelAttribute("task") Task task, @PathVariable("id") int id,
			RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
//		String nameAssign = staffService.findOne(task.getStaffId().getStaffId()).getFullName();
//		task.setNameAssign(nameAssign);
		taskService.saveTask(task);
		modelAndView.addObject("project", projectService.getProjecByiD(id));
		modelAndView.setViewName("redirect:/project/{id}/task");
		redirect.addFlashAttribute("notification", "bạn thực hiện action thành công !");
		return modelAndView;
	}

	// edit task
	@GetMapping(value = "project/{idproject}/task/{id}/edit")
	public ModelAndView edit(@PathVariable("idproject") int idproject, @PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);

		Task task = taskService.findById(id);
		modelAndView.addObject("task", task);
		List<Staff> listStaff = projectService.getListStaffOfProject(task.getProjectId().getProjectId());
		Map<Integer, String> staffs = new HashMap<>();
		listStaff.forEach(item -> staffs.put(item.getStaffId(), item.getName()));
		modelAndView.addObject("staffs", staffs);
		modelAndView.addObject("project", projectService.getProjecByiD(idproject));
		modelAndView.setViewName("taskform");
		return modelAndView;
	}

	@PostMapping(value = "project/{id}/staff/{idstaff}/tasks/add")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> addTaskToStaff(@PathVariable("id") int id, @PathVariable("idstaff") int idstaff,
			@RequestBody TaskDTO taskDTO) {
		Task task = new Task(taskDTO.getTaskName(), taskDTO.getNameCreate(), taskDTO.getDateCreate(),
				taskDTO.getDateStart(), taskDTO.getDeadlineDate(), taskDTO.getFinishDate(), taskDTO.getTaskState(),
				taskDTO.getDiscription(), taskDTO.getTaskOutput());

		Project project = projectService.getProjecByiD(id).get();
		if (project != null) {
			task.setProjectId(project);
		}
		Staff staff = staffService.findOne(idstaff);
		if (staff != null) {
			task.setStaffId(staff);
			;
		}

		taskService.saveTask(task);
		return new ResponseEntity<>(new ResponseMessage("Create Task Successfully!"), HttpStatus.OK);
	}

	@PostMapping(value = "/tasks/{id}/addsubtask")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> addSubTask(@PathVariable("id") int id, @RequestBody TaskDTO taskDTO) {

		Task parentTask = taskService.findById(id);
		Task task = new Task(taskDTO.getTaskName(), taskDTO.getNameCreate(), taskDTO.getDateCreate(),
				taskDTO.getDateStart(), taskDTO.getDeadlineDate(), taskDTO.getFinishDate(), taskDTO.getTaskState(),
				taskDTO.getDiscription(), taskDTO.getTaskOutput());
		if (parentTask != null) {
			task.setProjectId(parentTask.getProjectId());
			task.setTaskIdparent(parentTask.getTaskId());
			task.setStaffId(parentTask.getStaffId());
		}
		taskService.saveTask(task);
		return new ResponseEntity<>(new ResponseMessage("Create SubTask Successfully!"), HttpStatus.OK);
	}

	@GetMapping(value = "/task/detail/{id}")
	public ModelAndView getTask(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);

		modelAndView.addObject("task", taskService.findById(id));
		modelAndView.setViewName("detailtask");
		return modelAndView;
	}

	@DeleteMapping(value = "project/{id}/task/delete/{idtask}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> deleteTask(@PathVariable("id") int id, @PathVariable("idtask") int idtask) {
		List<Task> listTaskOfProject = new ArrayList<Task>();
		listTaskOfProject = projectService.getListTaskOfProject(id);
		Task _task = taskService.findById(idtask);
		for (int i = 0; i < listTaskOfProject.size(); i++) {
			if (_task.getTaskId() == listTaskOfProject.get(i).getTaskIdparent()) {
				System.out.println("task nay dang co task con nen ban phai xoa task con truoc !");
				String message = "Task " + _task.getTaskName()
						+ " co task con, neu muon xoa ban phai xoa task con truoc!";
				return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.PRECONDITION_REQUIRED);
			}
		}
		taskService.deleteTask(idtask);
		return new ResponseEntity<>(new ResponseMessage("Delete Task Successfully!"), HttpStatus.OK);
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

	// assign task
	@GetMapping(value = "project/{projectId}/task/{taskId}/assign")
	public ModelAndView assignTask(@PathVariable("projectId") int projectId, @PathVariable("taskId") int taskId,
			RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();

		Task task = taskService.findById(taskId);
		modelAndView.addObject("task", task);
		List<Staff> listStaff = projectService.getListStaffOfProject(task.getProjectId().getProjectId());
		Map<Integer, String> staffs = new HashMap<>();
		listStaff.forEach(item -> staffs.put(item.getStaffId(), item.getName()));
		modelAndView.addObject("staffs", staffs);
		modelAndView.addObject("project", projectService.getProjecByiD(projectId));
		modelAndView.setViewName("assignTask");
		redirect.addFlashAttribute("message", "bạn thực hiện assign task thành công !");
		return modelAndView;
	}
}
