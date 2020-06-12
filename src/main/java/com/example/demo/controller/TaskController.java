package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "projects/{id}/staff/{idstaff}/addTask")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> addTaskToStaff(@PathVariable("id") int id, @PathVariable("idstaff") int idstaff,
                                            @RequestBody TaskDTO taskDTO) {
        if (taskDTO.getDeadlineDate().before(taskDTO.getDateStart())) {
            return new ResponseEntity<>(new ResponseMessage("The end date cannot be before the start date!"),
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
                .build();

        Project project = projectService.getProjecByiD(id).get();
        if (project != null) {
            task.setProjectId(project);
        }
        Staff staff = staffService.findOne(idstaff);
        if (staff != null) {
            task.setStaffId(staff);
        }

        Set<String> listIdOfPreviousTask = taskDTO.getPreviousTask();
        Set<Task> listPreviousTaskOfProject = new HashSet<>();

        listIdOfPreviousTask.forEach(taskName -> {
            Task previousTask = taskService.findByTaskName(taskName);
            if (previousTask != null) {
                listPreviousTaskOfProject.add(previousTask);
            }
        });
        task.setPreviousTask(listPreviousTaskOfProject);

        taskService.saveTask(task);
        return new ResponseEntity<>(new ResponseMessage("Create Task Successfully!"), HttpStatus.OK);
    }

    @PostMapping(value = "/tasks/{id}/addsubtask")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> addSubTask(@PathVariable("id") int id, @RequestBody TaskDTO taskDTO) {
        if (taskDTO.getDeadlineDate().before(taskDTO.getDateStart())) {
            return new ResponseEntity<>(new ResponseMessage("The end date cannot be before the start date!"),
                    HttpStatus.BAD_REQUEST);
        }
        Task parentTask = taskService.findById(id);
        Task task = Task.builder()
                .taskName(taskDTO.getTaskName())
                .nameCreate(taskDTO.getNameCreate())
                .dateCreate(taskDTO.getDateCreate())
                .dateStart(taskDTO.getDateStart())
                .deadlineDate(taskDTO.getDeadlineDate())
                .discription(taskDTO.getDiscription())
                .taskOutput(taskDTO.getTaskOutput())
                .build();

        if (parentTask != null) {
            task.setProjectId(parentTask.getProjectId());
            task.setTaskIdparent(parentTask.getTaskId());
            task.setStaffId(parentTask.getStaffId());
        }

        Set<String> listIdOfPreviousTask = taskDTO.getPreviousTask();
        Set<Task> listPreviousTaskOfProject = new HashSet<>();

        listIdOfPreviousTask.forEach(taskName -> {
            Task previousTask = taskService.findByTaskName(taskName);
            if (previousTask != null) {
                listPreviousTaskOfProject.add(previousTask);
            }
        });
        task.setPreviousTask(listPreviousTaskOfProject);

        taskService.saveTask(task);
        return new ResponseEntity<>(new ResponseMessage("Create SubTask Successfully!"), HttpStatus.OK);
    }

    @PutMapping(value = "/task/{id}/edit")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> editTaskForProject(@PathVariable("id") int id, @RequestBody TaskDTO taskDTO) {
        if (taskDTO.getDeadlineDate().before(taskDTO.getDateStart())) {
            return new ResponseEntity<>(new ResponseMessage("The end date cannot be before the start date!"),
                    HttpStatus.BAD_REQUEST);
        }
        Task task = taskService.findById(id);
        if (task != null) {
            task.setTaskName(taskDTO.getTaskName());
            task.setNameCreate(taskDTO.getNameCreate());
            task.setDateCreate(taskDTO.getDateCreate());
            task.setDateStart(taskDTO.getDateStart());
            task.setDeadlineDate(taskDTO.getDeadlineDate());
            task.setDiscription(taskDTO.getDiscription());
            task.setTaskOutput(taskDTO.getTaskOutput());

            Staff staff = staffService.findByName(taskDTO.getStaffName());
            if(staff != null) {
                task.setStaffId(staff);
            }

            Set<String> listIdOfPreviousTask = taskDTO.getPreviousTask();
            Set<Task> listPreviousTaskOfProject = new HashSet<>();

            listIdOfPreviousTask.forEach(taskName -> {
                Task previousTask = taskService.findByTaskName(taskName);
                if (previousTask != null) {
                    listPreviousTaskOfProject.add(previousTask);
                }
            });
            task.setPreviousTask(listPreviousTaskOfProject);

            taskService.saveTask(task);
            return new ResponseEntity<>(new ResponseMessage("Edit Task Successfully!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Task not found!"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/staff/{idStaff}/task/{id}/edit")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> editTaskForStaff(@PathVariable("idStaff") int idStaff, @PathVariable("id") int id, @RequestBody TaskDTO taskDTO) {
        if (taskDTO.getDeadlineDate().before(taskDTO.getDateStart())) {
            return new ResponseEntity<>(new ResponseMessage("The end date cannot be before the start date!"),
                    HttpStatus.BAD_REQUEST);
        }
        Task task = taskService.findById(id);
        if (task != null) {
            task.setTaskName(taskDTO.getTaskName());
            task.setNameCreate(taskDTO.getNameCreate());
            task.setDateCreate(taskDTO.getDateCreate());
            task.setDateStart(taskDTO.getDateStart());
            task.setDeadlineDate(taskDTO.getDeadlineDate());
            task.setDiscription(taskDTO.getDiscription());
            task.setTaskOutput(taskDTO.getTaskOutput());

            Set<String> listIdOfPreviousTask = taskDTO.getPreviousTask();
            Set<Task> listPreviousTaskOfProject = new HashSet<>();

            listIdOfPreviousTask.forEach(taskName -> {
                Task previousTask = taskService.findByTaskName(taskName);
                if (previousTask != null) {
                    listPreviousTaskOfProject.add(previousTask);
                }
            });
            task.setPreviousTask(listPreviousTaskOfProject);

            taskService.saveTask(task);
            return new ResponseEntity<>(new ResponseMessage("Edit Task Successfully!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Task not found!"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/tasks/{id}")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> detailTask(@PathVariable("id") int id) {
        Task task = taskService.findById(id);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "projects/{id}/task/delete/{idtask}")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable("id") int id, @PathVariable("idtask") int idtask) {
        List<Task> listTaskOfProject = new ArrayList<Task>();
        listTaskOfProject = projectService.getListTaskOfProject(id);
        Task _task = taskService.findById(idtask);
        for (int i = 0; i < listTaskOfProject.size(); i++) {
            if (_task.getTaskId() == listTaskOfProject.get(i).getTaskIdparent()) {
                System.out.println("task nay dang co task con nen ban phai xoa task con truoc !");
                String message = "Task " + _task.getTaskName()
                        + " has sub task. If you want to delete this task, you must delete this previous sub task!";
                return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.PRECONDITION_REQUIRED);
            }
        }
        taskService.deleteTask(idtask);
        return new ResponseEntity<>(new ResponseMessage("Delete Task Successfully!"), HttpStatus.OK);
    }

    @PostMapping(value = "staffs/{idstaff}/tasks/{taskId}/assign")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<?> assignStaffForTask(@PathVariable("idstaff") int idstaff,
                                                @PathVariable("taskId") int taskId) {
        Task task = taskService.findById(taskId);
        Staff staff = staffService.findOne(idstaff);
        if (staff != null) {
            task.setStaffId(staff);
            ;
        }
        taskService.saveTask(task);
        return new ResponseEntity<>(new ResponseMessage("Assign Staff For Task Successfully!"), HttpStatus.OK);
    }

    @GetMapping(value = "/projects/{id}/previousTasks")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public Set<Task> getPreviousTaskInProject(@PathVariable int id) {
        Set<Task> listPreviousTaskOfProject = taskService.findListPreviousTask(id);
        return listPreviousTaskOfProject;
    }

    @GetMapping(value = "/projects/{id}/task/{idTask}/previousTasks")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public Set<Task> getPreviousTaskOfSubTaskInProject(@PathVariable int id, @PathVariable int idTask) {
        Set<Task> listPreviousTaskOfSubTaskInProject = taskService.findListPreviousOfSubTask(id, idTask);
        return listPreviousTaskOfSubTaskInProject;
    }

    @GetMapping(value = "/task/{id}/previousTask")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public Set<Task> findByPreviousTask(@PathVariable int id) {
        Set<Task> listPreviousTaskOfTask = taskService.findByPreviousTask(id);
        return listPreviousTaskOfTask;
    }
}
