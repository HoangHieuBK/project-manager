package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.demo.dto.TaskProgressDTO;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectProgress;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskProgress;
import com.example.demo.service.FeedBackService;
import com.example.demo.service.ProjectProgressService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskProgressService;
import com.example.demo.service.TaskService;
import com.example.demo.validation.Util;

@CrossOrigin(origins = "*")
@RestController
public class ChartController {
    @Autowired
    private FeedBackService feebackService;
    @Autowired
    private TaskProgressService taskProgressService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectProgressService projectProgressService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks/{id}/taskProgresses")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> listTaskProgresses(@PathVariable("id") int id) {
        List<TaskProgress> workLogList = taskProgressService.findByTaskIDOrderByDateCreateAsc(id);
        if (workLogList.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Tiến độ công việc không tồn tại!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workLogList, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}/labelFromListDate")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public List<String> listLabelTask(@PathVariable("id") int id) {
        Task task = taskService.findById(id);
        List<TaskProgress> workLogList = taskProgressService.findByTaskIDOrderByDateCreateAsc(id);
        List<Date> listDate;
        if (workLogList.size() > 0) {
            listDate = Util.getListDate(task.getDateStart(), task.getDeadlineDate(),
                    workLogList.get(workLogList.size() - 1).getDateLog());
        } else {
            listDate = Util.getListDate(task.getDateStart(), task.getDeadlineDate(), new Date(0));
        }
        List<String> listLabelsFromListDate = Util.getLabelFromListDate(listDate);

        return listLabelsFromListDate;
    }

    @GetMapping("/tasks/{id}/listExpectProgresses")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public List<Double> listTaskExpectProgresses(@PathVariable("id") int id) {
        Task task = taskService.findById(id);
        List<Double> listExpectProgresses = Util.getListExpectProgress(task.getDateStart(), task.getDeadlineDate());
        return listExpectProgresses;
    }

    @GetMapping("/tasks/{id}/listActualProgresses")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public List<Double> getListTaskActualProgress(@PathVariable("id") int id) {
        Task task = taskService.findById(id);
        List<TaskProgress> workLogList = taskProgressService.findByTaskIDOrderByDateCreateAsc(id);
        List<Double> listActualProgresses = Util.getListActualProgress(task.getDateStart(), workLogList);
        return listActualProgresses;
    }

    @PostMapping("/tasks/{id}/addTaskProgress")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addTaskProgress(@PathVariable("id") int id, @RequestBody TaskProgressDTO taskprogressDTO) {
        TaskProgress taskProgress = TaskProgress.builder()
                                    .dateLog(taskprogressDTO.getDateLog())
                                    .progress(taskprogressDTO.getProgress())
                                    .detailLog(taskprogressDTO.getDetailLog())
                                    .build();

        Task task = taskService.findById(id);
        taskProgress.setTaskId(task);
        taskProgressService.createTaskProgress(taskProgress);
        return new ResponseEntity<>(new ResponseMessage("Ghi nhật kí lịch trình công việc thành công!"), HttpStatus.OK);
    }


    @GetMapping("/projects/{id}/projectProgresses")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> listProjectProgresses(@PathVariable("id") int id) {
        List<ProjectProgress> workLogList = new ArrayList<>();
        workLogList = projectProgressService.findByProjectIDOrderByDateCreateAsc(id);
        if (workLogList.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Danh sách tiến độ công việc trống!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workLogList, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}/labelProjectFromListDate")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public List<String> listLabelProject(@PathVariable("id") int id) {
        Project project = projectService.findByProjectId(id);
        List<ProjectProgress> workLogList = projectProgressService.findByProjectIDOrderByDateCreateAsc(id);
        List<Date> listDate;
        if (workLogList.size() > 0) {
            listDate = Util.getListDate(project.getStartDate(), project.getDeadlineDate(),
                    workLogList.get(workLogList.size() - 1).getDateLog());
        } else {
            listDate = Util.getListDate(project.getStartDate(), project.getDeadlineDate(), new Date(0));
        }
        List<String> listLabelProjectFromListDate = Util.getLabelFromListDate(listDate);

        return listLabelProjectFromListDate;
    }

    @GetMapping("/projects/{id}/listProjectExpectProgresses")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public List<Double> listProjectExpectProgresses(@PathVariable("id") int id) {
        Project project = projectService.findByProjectId(id);
        List<Double> listProjectExpectProgresses = Util.getListExpectProgress(project.getStartDate(), project.getDeadlineDate());
        return listProjectExpectProgresses;
    }

    @GetMapping("/projects/{id}/listProjectActualProgresses")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public List<Double> getListProjectActualProgress(@PathVariable("id") int id) {
        Project project = projectService.findByProjectId(id);
        List<ProjectProgress> workLogList = projectProgressService.findByProjectIDOrderByDateCreateAsc(id);
        List<Double> listProjectActualProgresses = Util.getListActualProjectProgress(project.getStartDate(), workLogList);
        return listProjectActualProgresses;
    }

}
