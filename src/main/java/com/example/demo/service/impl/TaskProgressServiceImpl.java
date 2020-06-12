package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectProgress;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskProgress;
import com.example.demo.repository.TaskProgressRepo;
import com.example.demo.service.ProjectProgressService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskProgressService;
import com.example.demo.service.TaskService;
import com.example.demo.utility.Util;

@Service
public class TaskProgressServiceImpl implements TaskProgressService {
    @Autowired
    private TaskProgressRepo taskProgressRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectProgressService projectProgressService;

    @Autowired
    private ProjectService projectService;

    @Override
    public Date getStartDate(int idTask) {
        // TODO Auto-generated method stub
        return taskProgressRepo.getStartDate(idTask);
    }

    @Override
    public Date getDeadline(int idTask) {
        // TODO Auto-generated method stub
        return taskProgressRepo.getDeadline(idTask);
    }

    @Override
    public List<TaskProgress> getListTaskProgressByIdTask(int idTask) {
        // TODO Auto-generated method stub
        return taskProgressRepo.getListTaskProgressByIdTask(idTask);
    }

    @Override
    public List<TaskProgress> findByTaskIDOrderByDateCreateAsc(int taskId) {
        // TODO Auto-generated method stub
        return taskProgressRepo.findByTaskIDOrderByDateCreateAsc(taskId);
    }

    @Override
    public TaskProgress save(TaskProgress taskProgress) {
        // TODO Auto-generated method stub
        return taskProgressRepo.save(taskProgress);
    }

    @Override
    public TaskProgress findLastTaskProgressOfTaskBefore(Date moment, Integer taskId) {
        Date secondLastDate = taskProgressRepo.findLatestTaskDateBefore(taskId, moment);
        TaskProgress taskProgress = taskProgressRepo.findByTaskIdAndDateLog(taskId, secondLastDate);
        return taskProgress;
    }

    /*******************************************************************************************************************
     * Neu tao TaskProgress co dateCreate = X cho 1 task co task cha => ham se tao TaskProgress cho
     * task cha, su dung so lieu lay tu cac TaskProgress duoc tao muon nhat truoc ngay X cua cac task
     * con cua task cha do Neu khong co ban ghi TaskProgress nao cua task tuong ung co ngay muon hon
     * ngay X thi se cap nhat progress cho task do
     *
     * @param TaskProgress
     */

    @Override
    @Transactional
    public void createTaskProgress(TaskProgress taskProgress) {
        Task task = taskProgress.getTaskId();
        Date latestDate = taskProgressRepo.getLastDate(task.getTaskId());
        // xem đã tồn tại taskProgress trong bảng TaskProgress chưa
        TaskProgress sameTimeTaskProgress = taskProgressRepo
                .findByTaskIdAndDateLog(taskProgress.getTaskId().getTaskId(), taskProgress.getDateLog());
        if (sameTimeTaskProgress == null) {
            taskProgressRepo.save(taskProgress);
        } else {
            sameTimeTaskProgress.setProgress(taskProgress.getProgress());
            sameTimeTaskProgress.setDetailLog(taskProgress.getDetailLog());
            taskProgressRepo.save(sameTimeTaskProgress);
        }
        System.out.println(latestDate);
        // nếu task có task cha thì nó là task độc lập, task cha sẽ là task phụ thuộc
        if (task.getTaskIdparent() != null) {
            TaskProgress taskParentProgress;
            int idParentTask = task.getTaskIdparent();
            // Nhap thong tin work log cho task cha cua no
            Task taskParent = taskService.findById(idParentTask);
            taskParentProgress = taskProgressRepo.findByTaskIdAndDateLog(taskParent.getTaskId(),
                    taskProgress.getDateLog());
            if (taskParentProgress == null) {
                taskParentProgress = new TaskProgress();
                taskParentProgress.setTaskId(taskService.findById(idParentTask));
                taskParentProgress.setDateLog(taskProgress.getDateLog());
                taskParentProgress
                        .setDetailLog(task.toString() + ": " + taskProgress.getDetailLog() + "\n");
            } else {
                String formerDetailLog = taskParentProgress.getDetailLog();
                taskParentProgress.setDetailLog(
                        formerDetailLog + task.toString() + ": " + taskProgress.getDetailLog() + "\n");
            }
            long tRest;
            long tRestMax = 0L;
            // tìm tất cả các task con của task cha có taskIdParent
            List<Task> childTaskList = taskService.findAllByParentTaskId(idParentTask);
            for (Task childTask : childTaskList) {
                tRest = 0L;
                if (childTask.getPreviousTask().isEmpty()) {
                    tRest = Util.getTrest(childTask, taskProgress.getDateLog(), this);
                } else {
                    while (!childTask.getPreviousTask().isEmpty()) {
                        tRest += Util.getTrest(childTask, taskProgress.getDateLog(), this);
                        childTask = childTask.getPreviousTask().iterator().next();
                    }
                }
                if (tRest > tRestMax) {
                    tRestMax = tRest;
                }
            }
            int progressTask = 100 - (int) (100 * tRestMax
                    / (taskParent.getDeadlineDate().getTime() - taskParent.getDateStart().getTime()));
            if (progressTask < 0) {
                progressTask = 0;
            }
            taskParentProgress.setProgress(progressTask);
            createTaskProgress(taskParentProgress);
        } else {
            ProjectProgress projectProgress;
            projectProgress = projectProgressService
                    .findByProjectIdAndDateLog(task.getProjectId().getProjectId(), taskProgress.getDateLog());
            if (projectProgress == null) {
                projectProgress = new ProjectProgress();
                projectProgress.setProjectId(task.getProjectId());
                projectProgress.setDateLog(taskProgress.getDateLog());
                projectProgress.setDetailLog(task.toString() + ": " + taskProgress.getDetailLog() + "\n");
            } else {
                projectProgress.setDetailLog(projectProgress.getDetailLog() + task.toString() + ": "
                        + taskProgress.getDetailLog() + "\n");
            }
            int projectId = task.getProjectId().getProjectId();
            List<Task> taskList = taskService.findByProjectIdAndTaskIdParentIsNull(projectId);
            long tRest;
            long tRestMax = 0L;
            for (Task bigTask : taskList) {
                tRest = Util.getTrestOfBigTask(bigTask, taskProgress.getDateLog(), this);
                if (!bigTask.getPreviousTask().isEmpty()) {
                    while (!bigTask.getPreviousTask().isEmpty()) {
                        bigTask = bigTask.getPreviousTask().iterator().next();
                        tRest += Util.getTrestOfBigTask(bigTask, taskProgress.getDateLog(), this);
                    }
                }
                if (tRest > tRestMax) {
                    tRestMax = tRest;
                }
                System.out
                        .println("Task: " + bigTask.getTaskId() + " tRest: " + tRest / (3600L * 24L * 1000));
            }
            System.out.println("tRestMax: " + tRestMax);
//			System.out.println("Project duration: "
//					+ (task.getProjectId().getFinishDate().getTime() - task.getProjectId().getStartDate().getTime()));
            int progressProject =
                    100 - (int) (100 * tRestMax / (task.getProjectId().getDeadlineDate().getTime()
                            - task.getProjectId().getStartDate().getTime()));
            if (progressProject < 0) {
                progressProject = 0;
            }
            Date lastProjectDate =
                    projectProgressService.getLastDateOfProjectProgress(task.getProjectId().getProjectId());
            projectProgress.setProgress(progressProject);
            if (lastProjectDate == null
                    || (lastProjectDate != null && lastProjectDate.before(taskProgress.getDateLog()))) {
                Project project = task.getProjectId();
                project.setProjectState(progressProject);

                if (progressProject == 100) {
                    project.setFinishDate(projectProgress.getDateLog());
                }
                projectService.saveProject(project);
            }
            projectProgressService.save(projectProgress);
        }
        if (latestDate == null
                || (latestDate != null && latestDate.before(taskProgress.getDateLog()))) {
            task.setTaskState(taskProgress.getProgress());
            if (taskProgress.getProgress() == 100) {
                task.setFinishDate(taskProgress.getDateLog());
            }
            taskService.saveTask(task);
        }
    }

    @Override
    public void deleteTaskProgressById(long id) {
        // TODO Auto-generated method stub
        taskProgressRepo.deleteById(id);
    }

}
