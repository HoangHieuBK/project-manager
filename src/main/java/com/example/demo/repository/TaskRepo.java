package com.example.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Task;


@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {

	List<Task> findByTaskIdparent(int idParentTask);

	Task findByTaskId(int taskId);

	Task findByTaskName(String taskName);

	@Query("select t.previousTask from Task t where t.taskId = :taskId")
	Set<Task> findByPreviousTask(int taskId);

	@Query("select t from Task t where t.projectId.projectId= :projectId and t.taskIdparent = null ")
    List<Task> findByProjectIdAndTaskIdParentIsNull(int projectId);

	@Query("select t from Task t where t.projectId.projectId= :projectId and t.taskIdparent = null ")
	Set<Task> findByPreviousTaskOfParentTask(int projectId);

	@Query("select t from Task t where t.projectId.projectId= :projectId and t.taskIdparent = :taskIdParent ")
	Set<Task> findByPreviousTaskOfSubTask(int projectId, int taskIdParent);

}
