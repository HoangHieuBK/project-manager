package com.example.demo.service;


import java.util.List;

import com.example.demo.entity.Task;

public interface TaskService {
    void saveTask(Task task);

    Task findById(int id);
    
    boolean deleteTask(int id);

	List<Task> findAllByParentTaskId(int idParentTask);

	List<Task> findByProjectIdAndTaskIdParentIsNull(int projectId);

}
