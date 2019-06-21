package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Task;
@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
	@Query("select t from Task t where t.taskIdparent= :idParentTask")
	List<Task> findAllByParentTaskId(int idParentTask);

	@Query("select t from Task t where t.projectId.projectId= :projectId and t.taskIdparent = null ")
    List<Task> findByProjectIdAndTaskIdParentIsNull(int projectId);
}
