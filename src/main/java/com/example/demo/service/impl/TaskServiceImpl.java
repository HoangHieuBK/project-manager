package com.example.demo.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepo;
import com.example.demo.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepo taskRepo;

	@Override
	public void saveTask(Task task) {
		taskRepo.save(task);
	}

	@Override
	public Task findById(int id) {
		return taskRepo.findByTaskId(id);
	}

	@Override
	public Task findByTaskName(String taskName) {
		return taskRepo.findByTaskName(taskName);
	}

	@Override
	public boolean deleteTask(int id) {
		 taskRepo.deleteById(id);
		 return true;
	}

	@Override
	public List<Task> findAllByParentTaskId(int idParentTask) {
		return taskRepo.findByTaskIdparent(idParentTask);
	}

	@Override
	public List<Task> findByProjectIdAndTaskIdParentIsNull(int projectId) {
		return taskRepo.findByProjectIdAndTaskIdParentIsNull(projectId);
	}

	@Override
	public Set<Task> findListPreviousTask(int projectId) {
		return taskRepo.findByPreviousTaskOfParentTask(projectId);
	}

	@Override
	public Set<Task> findListPreviousOfSubTask(int projectId, int idParentTask) {
		return taskRepo.findByPreviousTaskOfSubTask(projectId, idParentTask);
	}

	@Override
	public Set<Task> findByPreviousTask(int idTask) {
		return taskRepo.findByPreviousTask(idTask);
	}
}
