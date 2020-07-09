package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.service.StaffService;
import com.example.demo.utility.Util;
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

	@Autowired
	StaffService staffService;

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

			ProjectDTO projectDTO = ProjectDTO.builder()
									.projectId(project.getProjectId())
									.projectName(project.getProjectName())
									.createDate(project.getCreateDate())
									.startDate(project.getStartDate())
									.deadlineDate(project.getDeadlineDate())
									.finishDate(project.getFinishDate())
									.description(project.getDescription())
					 				.projectState(project.getProjectState())
									.projectOutput(project.getProjectOutput())
									.build();

			int numOfStaff = project.getStaffProject().size();
			projectDTO.setNumberOfStaff(numOfStaff);
			int numOfTask = project.getTask().size();
			projectDTO.setNumberOfTask(numOfTask);

			return new ResponseEntity<>(projectDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseMessage("Dự án không tồn tại!"), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/projects/add")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO) {
		if (projectService.existByProjectName(projectDTO.getProjectName())) {
			return new ResponseEntity<>(new ResponseMessage("Lỗi -> Dự án đã tồn tại!"),
					HttpStatus.BAD_REQUEST);
		}
		if (projectDTO.getDeadlineDate().before(projectDTO.getStartDate())) {
			return new ResponseEntity<>(new ResponseMessage("Ngày kết thúc không thể trước ngày bắt đầu"),
					HttpStatus.BAD_REQUEST);
		}
		Project project = Project.builder()
						  .projectName(projectDTO.getProjectName())
				   	      .createDate(projectDTO.getCreateDate())
						  .startDate(projectDTO.getStartDate())
						  .deadlineDate(projectDTO.getDeadlineDate())
						  .description(projectDTO.getDescription())
						  .projectOutput(projectDTO.getProjectOutput())
				          .projectState(0)
						  .build();

		projectService.saveProject(project);
		return new ResponseEntity<>(new ResponseMessage("Tạo dự án thành công!"), HttpStatus.OK);
	}



	@PutMapping("/projects/edit/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> editProject(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO) {

		if (projectDTO.getDeadlineDate().before(projectDTO.getStartDate())) {
			return new ResponseEntity<>(new ResponseMessage("Ngày kết thúc không thể trước ngày bắt đầu!"),
					HttpStatus.BAD_REQUEST);
		}
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

			return new ResponseEntity<>(new ResponseMessage("Sửa dự án thành công!"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseMessage("Dự án không tồn tại!"), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/projects/delete/{id}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteProject(@PathVariable int id) {
		System.out.println("Delete project with ID = " + id + "...");
		projectService.deleteProjectById(id);
		return new ResponseEntity<>(new ResponseMessage("Dự án đã được xóa!"), HttpStatus.OK);
	}

	@GetMapping(value = "/projects/{id}/staffs")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<StaffDTO> getstaffInProject(@PathVariable int id) {
		List<Staff> listStaffOfProject = projectService.getListStaffOfProject(id);

		List<StaffDTO> listStaffDTO = new ArrayList<StaffDTO>();

		listStaffOfProject.forEach(staff -> {
			StaffDTO _staffDTO = StaffDTO.builder()
								.staffId(staff.getStaffId())
								.name(staff.getName())
								.gender(staff.getGender())
								.possition(staff.getPossition())
								.skill(staff.getSkill())
								.telephone(staff.getTelephone())
								.description(staff.getDescription())
								.departmentName(staff.getDepartmentId().getDepartmentName())
								.accountName(staff.getAccountId().getAccountName())
								.build();

			listStaffDTO.add(_staffDTO);
		});
		return listStaffDTO;
	}

	@GetMapping(value = "/projects/{id}/tasks")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<TaskDTO> getTasksInProject(@PathVariable int id) {
		List<Task> listTaskOfProject = projectService.getListTaskOfProject(id);
		List<TaskDTO> listTaskDTO = new ArrayList<>();

		listTaskOfProject.forEach(task -> {

			TaskDTO _taskDTO = TaskDTO.builder()
								.taskId(task.getTaskId())
								.taskIdParent(task.getTaskIdparent())
								.taskName(task.getTaskName())
								.nameCreate(task.getNameCreate())
								.dateCreate(task.getDateCreate())
								.dateStart(task.getDateStart())
								.deadlineDate(task.getDeadlineDate())
								.taskState(task.getTaskState())
								.discription(task.getDiscription())
								.taskOutput(task.getTaskOutput())
								.build();

			if (task.getStaffId() != null) {
				_taskDTO.setStaffName(task.getStaffId().getName());
				_taskDTO.setStaffId(task.getStaffId().getStaffId());
			}
			listTaskDTO.add(_taskDTO);
		});
		return listTaskDTO;
	}

	@PostMapping(value = "/projects/{id}/addTask")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> addTaskToProject(@PathVariable("id") int id, @RequestBody TaskDTO taskDTO) {

		if (Util.checkEndDateBeforeStartDate(taskDTO.getDateStart(), taskDTO.getDeadlineDate())) {
			return new ResponseEntity<>(new ResponseMessage("Ngày kết thúc không thể trước ngày bắt đầu!"),
					HttpStatus.BAD_REQUEST);
		}

		Task task = Task.builder()
					.taskName(taskDTO.getTaskName())
					.nameCreate(taskDTO.getNameCreate())
					.dateCreate(taskDTO.getDateCreate())
					.dateStart(taskDTO.getDateStart())
					.deadlineDate(taskDTO.getDeadlineDate())
					.discription(taskDTO.getDiscription())
					.taskOutput(taskDTO.getTaskOutput())
				    .taskState(0)
					.build();

		Project project = projectService.getProjecByiD(id).get();
		if (project != null) {
			task.setProjectId(project);
		}

		Staff _staff = staffService.findByName(taskDTO.getStaffName());
		if(_staff != null) {
			task.setStaffId(_staff);
		}

        Set<String> listIdOfPreviousTask= taskDTO.getPreviousTask();
		Set<Task> listPreviousTaskOfProject = new HashSet<>();

		listIdOfPreviousTask.forEach(taskName ->{
			Task previousTask = taskService.findByTaskName(taskName);
			if (previousTask != null){
				listPreviousTaskOfProject.add(previousTask);
			}
		});
		task.setPreviousTask(listPreviousTaskOfProject);

		taskService.saveTask(task);
		return new ResponseEntity<>(new ResponseMessage("Tạo công việc thành công!"), HttpStatus.OK);
	}


	@GetMapping(value = "/projects/{id}/staffsNotIn")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
	public List<StaffDTO> getstaffNotInProject(@PathVariable int id) {
		List<Staff> listStaffNotInProject = projectService.getListStaffNotInProject(id);
		List<StaffDTO> listStaffDTO = new ArrayList<StaffDTO>();

		listStaffNotInProject.forEach(staff -> {

			StaffDTO _staffDTO = StaffDTO.builder()
					.staffId(staff.getStaffId())
					.name(staff.getName())
					.gender(staff.getGender())
					.possition(staff.getPossition())
					.skill(staff.getSkill())
					.telephone(staff.getTelephone())
					.description(staff.getDescription())
					.departmentName(staff.getDepartmentId().getDepartmentName())
					.accountName(staff.getAccountId().getAccountName())
					.build();
			listStaffDTO.add(_staffDTO);
		});
		return listStaffDTO;
	}

	@PostMapping("/projects/{id}/staff/add/{idStaff}")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> addStaffInproject(@PathVariable int id, @PathVariable int idStaff) {
		projectService.addStaffInProject(id, idStaff);
		return new ResponseEntity<>(new ResponseMessage("Nhân viên đã được thêm vào dự án!"), HttpStatus.OK);
	}

	@GetMapping(value = "/projects/{id}/staff/{idStaff}/delete")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteStaffInProject(@PathVariable int id, @PathVariable int idStaff) {
		projectService.deleteStaffIdInProject(idStaff, id);
		return new ResponseEntity<>(new ResponseMessage("Nhân viên đã được xóa khỏi dự án!"), HttpStatus.OK);
	}

}
