package com.example.demo.service;


import java.util.List;
import java.util.Set;

import com.example.demo.entity.Task;

public interface TaskService {
    void saveTask(Task task);

    Task findById(int id);

    Task findByTaskName(String taskName);
    
    boolean deleteTask(int id);

	List<Task> findAllByParentTaskId(int idParentTask);

	List<Task> findByProjectIdAndTaskIdParentIsNull(int projectId);

	Set<Task> findListPreviousTask(int idTask);

	Set<Task> findListPreviousOfSubTask(int idSubTask, int idParentTask);

	Set<Task> findByPreviousTask(int idTask);
}
