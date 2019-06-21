package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {

//	public Project findByUsename(String projectname);

	@Query("select t from Task t where t.projectId.projectId= :projectId")
	List<Task> fetchStaffProjectInnerJoin(@Param("projectId") int projectId);

	@Query("select s from Staff s inner join s.staffProject sp  where sp.projectId= :projectId")
	List<Staff> fetchStaff(@Param("projectId") int projectId);
	
	@Query("select s from Staff s left join s.staffProject sp  where sp.projectId <> :projectId or s.staffId not in (select staffId from StaffProject)")
	List<Staff> getListStaffNotInproject(@Param("projectId") int projectId);
	
	@Modifying
	@Transactional
	@Query(value= "insert into Staff_Project(staff_id,project_id) value (:staffId, :projectId)", nativeQuery = true)
	void insertStaffInproject(@Param("staffId") int staffId, @Param("projectId") int projectId);
	
	@Modifying
	@Transactional
	@Query(value= "delete sp from Staff_Project sp where sp.staff_id= :staffId AND sp.project_id= :project_id", nativeQuery = true)
	void deleteStaffInProject(@Param("staffId") int staffId, @Param("project_id") int projectId);

	@Query(value = "select t from Task t where t.projectId.projectId= :id and t.taskIdparent = null")
	List<Task> getListBigTaskOfProject(int id);
}
