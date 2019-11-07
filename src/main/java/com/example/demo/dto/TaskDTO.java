package com.example.demo.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;

public class TaskDTO {

	private Integer taskId;
	private String taskName;
	private String nameCreate;
	private Date dateCreate;
	private Date dateStart;
	private Date deadlineDate;
	private Date finishDate;
	private Integer taskState;
	private String discription;
	private String taskOutput;
	private Project project;
	private Staff staff;
	private Set<Task> previousTask = new HashSet<>();

	public TaskDTO() {
		super();
	}

	public TaskDTO(String taskName, String nameCreate, Date dateCreate, Date dateStart, Date deadlineDate,
			Date finishDate, Integer taskState, String discription, String taskOutput) {
		super();
		this.taskName = taskName;
		this.nameCreate = nameCreate;
		this.dateCreate = dateCreate;
		this.dateStart = dateStart;
		this.deadlineDate = deadlineDate;
		this.finishDate = finishDate;
		this.taskState = taskState;
		this.discription = discription;
		this.taskOutput = taskOutput;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getNameCreate() {
		return nameCreate;
	}

	public void setNameCreate(String nameCreate) {
		this.nameCreate = nameCreate;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getTaskOutput() {
		return taskOutput;
	}

	public void setTaskOutput(String taskOutput) {
		this.taskOutput = taskOutput;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Set<Task> getPreviousTask() {
		return previousTask;
	}

	public void setPreviousTask(Set<Task> previousTask) {
		this.previousTask = previousTask;
	}
	
	
}
