package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TaskProgress;

@Repository
public interface TaskProgressRepo extends JpaRepository<TaskProgress, Long> {
	@Query("select t.dateLog from TaskProgress t where t.taskId.taskId = :idTask")
	List<Date> getListDateLogByIdTask(@Param("idTask") int idTask);

	@Query("select t.dateStart from Task t where t.taskId = :idTask ")
	Date getStartDate(@Param("idTask") int idTask);

	@Query("select t.deadlineDate from Task t where t.taskId = :idTask ")
	Date getDeadline(@Param("idTask") int idTask);

	@Query("select t from TaskProgress t where t.taskId.taskId = :idTask ")
	List<TaskProgress> getListTaskProgressByIdTask(@Param("idTask") int idTask);

	@Query("select t from TaskProgress t where t.taskId.taskId= :taskId ORDER BY t.dateLog ASC")
	List<TaskProgress> findByTaskIDOrderByDateCreateAsc(@Param("taskId") int taskId);

	@Query("select t from TaskProgress t where t.taskId.taskId = :taskId and t.dateLog = :dateLog")
	TaskProgress findByTaskIdAndDateLog(Integer taskId, Date dateLog);

	@Query("select max(t.dateLog) from TaskProgress t where t.taskId.taskId = :taskId and t.dateLog<= :moment")
	Date findLatestTaskDateBefore(Integer taskId, Date moment);

	@Query("select max(dateLog) from TaskProgress t where t.taskId.taskId= :taskId")
	Date getLastDate(Integer taskId);
	
	@Query("select max(t.dateLog) from TaskProgress t where t.taskId.taskId = :taskId and t.dateLog<= :dateLog")
    Date findLatestTaskDateBefore(int taskId, Date dateLog);

}
