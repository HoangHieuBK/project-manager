package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProjectProgress;
import com.example.demo.repository.ProjectProgressRepo;
import com.example.demo.service.ProjectProgressService;

@Service
public class ProjectProgressServiceImpl implements ProjectProgressService{

	@Autowired
	ProjectProgressRepo projectProgressRepo;
	
	@Override
	public ProjectProgress findByProjectIdAndDateLog(Integer projectId, Date dateLog) {
		// TODO Auto-generated method stub
		return projectProgressRepo.findByProjectIdAndDateLog(projectId, dateLog);
	}

	@Override
	public Date getLastDateOfProgjectProgress(Integer projectId) {
		// TODO Auto-generated method stub
		return projectProgressRepo.getLastDateOfProjectProgress(projectId);
	}

	@Override
	public void save(ProjectProgress projectProgress) {
		// TODO Auto-generated method stub
		projectProgressRepo.save(projectProgress);
	}

	@Override
	public List<ProjectProgress> findByProjectIDOrderByDateCreateAsc(int id) {
		// TODO Auto-generated method stub
		return projectProgressRepo.findByProjectIDOrderByDateCreateAsc(id);
	}

}
