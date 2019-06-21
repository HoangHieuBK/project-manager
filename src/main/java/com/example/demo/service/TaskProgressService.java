package com.example.demo.service;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.TaskProgress;


public interface TaskProgressService {

	Date getStartDate(int idTask);
	
	Date getDeadline(int idTask);
	
	List<TaskProgress> getListTaskProgressByIdTask(int idTask);
	
	List<TaskProgress> findByTaskIDOrderByDateCreateAsc(int taskId);
	
	TaskProgress save(TaskProgress taskProgress);

	TaskProgress findLastTaskProgressOfTaskBefore(Date moment, Integer taskId);

	void createTaskProgress(TaskProgress taskProgress);
	
	void deleteTaskProgressById(long id);
	
}
