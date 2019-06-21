package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepo;
import com.example.demo.service.ProjectProgressService;
import com.example.demo.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	@Autowired
	TaskRepo taskRepo;
	@Autowired
	ProjectProgressService projectProgressService;

	@Override
	public void saveTask(Task task) {
		taskRepo.save(task);
	}

	@Override
	public Task findById(int id) {
		return taskRepo.getOne(id);
	}

	@Override
	public boolean deleteTask(int id) {
		 taskRepo.deleteById(id);
		 return true;
	}

	@Override
	public List<Task> findAllByParentTaskId(int idParentTask) {
		return taskRepo.findAllByParentTaskId(idParentTask);
	}

	@Override
	public List<Task> findByProjectIdAndTaskIdParentIsNull(int projectId) {
		return taskRepo.findByProjectIdAndTaskIdParentIsNull(projectId);
	}
}
