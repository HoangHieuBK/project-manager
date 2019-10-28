package com.example.demo.dto;

import java.util.Collection;
import java.util.Date;

import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;

public class ProjectDTO {

	private Integer projectId;
	private String projectName;
	private Date createDate;
	private Date startDate;
	private Date deadlineDate;
	private Date finishDate;
	private String description;
	private Integer projectState;
	private String projectOutput;
	private Collection<String> staffsOfProject;
	private Collection<String> task;
	
	public ProjectDTO() {
		super();
	}

	public ProjectDTO(String projectName, Date createDate, Date startDate, Date deadlineDate, Date finishDate,
			String description, Integer projectState, String projectOutput) {
		super();
		this.projectName = projectName;
		this.createDate = createDate;
		this.startDate = startDate;
		this.deadlineDate = deadlineDate;
		this.finishDate = finishDate;
		this.description = description;
		this.projectState = projectState;
		this.projectOutput = projectOutput;
	}

	
	public ProjectDTO(String projectName, Date createDate, Date startDate, Date deadlineDate) {
		super();
		this.projectName = projectName;
		this.createDate = createDate;
		this.startDate = startDate;
		this.deadlineDate = deadlineDate;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getProjectState() {
		return projectState;
	}

	public void setProjectState(Integer projectState) {
		this.projectState = projectState;
	}

	public String getProjectOutput() {
		return projectOutput;
	}

	public void setProjectOutput(String projectOutput) {
		this.projectOutput = projectOutput;
	}

	public Collection<String> getStaffsOfProject() {
		return staffsOfProject;
	}

	public void setStaffsOfProject(Collection<String> staffsOfProject) {
		this.staffsOfProject = staffsOfProject;
	}

	public Collection<String> getTask() {
		return task;
	}

	public void setTask(Collection<String> task) {
		this.task = task;
	}

	
}
