package com.example.demo.service;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.ProjectProgress;

public interface ProjectProgressService {

	public ProjectProgress findByProjectIdAndDateLog(Integer projectId, Date dateLog);

	public Date getLastDateOfProgjectProgress(Integer projectId);

	public void save(ProjectProgress projectProgress);

	public List<ProjectProgress> findByProjectIDOrderByDateCreateAsc(int id);

}
