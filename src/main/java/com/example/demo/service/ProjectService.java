package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;

public interface ProjectService {

	List<Project> getListProject();

	void saveProject(Project project);

	Project getProjecByiD(int id);

	List<Task> getListTaskOfProject(int id);

	List<Staff> getListStaffOfProject(int id);

	boolean deleteProjectById(int id);

	List<Project> searchProject(String keyworld);
	
	void addStaffInProject(int idProject,int idStaff);
	
	List<Staff> getListStaffNotInProject(int idProject);
	
	Boolean deleteStaffIdInProject(int idStaff,int idProject);

	List<Task> getListBigTaskOfProject(int id);

	Project findByProjectId(int id);

}
