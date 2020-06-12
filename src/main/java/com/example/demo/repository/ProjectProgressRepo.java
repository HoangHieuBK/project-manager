package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.ProjectProgress;
import org.springframework.data.repository.query.Param;

public interface ProjectProgressRepo extends JpaRepository<ProjectProgress, Long> {

	@Query("select p from ProjectProgress p where p.projectId.projectId = :projectId and p.dateLog = :dateLog")
	public ProjectProgress findByProjectIdAndDateLog(Integer projectId, Date dateLog);

	@Query("select max(p.dateLog) from ProjectProgress p where p.projectId.projectId= :projectId")
	public Date getLastDateOfProjectProgress(@Param("projectId") int projectId);
	
	@Query("select p from ProjectProgress p where p.projectId.projectId= :id ORDER BY p.dateLog ASC")
	public List<ProjectProgress> findByProjectIDOrderByDateCreateAsc(int id);

}
