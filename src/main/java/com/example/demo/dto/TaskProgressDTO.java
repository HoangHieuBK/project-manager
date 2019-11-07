package com.example.demo.dto;

import java.util.Date;

import com.example.demo.entity.Task;

public class TaskProgressDTO {
	private Long progressId;
	private Date dateLog;
	private int progress;
	private String detailLog;
	private Task task;

	public TaskProgressDTO() {
		super();
	}

	public TaskProgressDTO(Date dateLog, int progress, String detailLog) {
		super();
		this.dateLog = dateLog;
		this.progress = progress;
		this.detailLog = detailLog;
	}

	public Long getProgressId() {
		return progressId;
	}

	public void setProgressId(Long progressId) {
		this.progressId = progressId;
	}

	public Date getDateLog() {
		return dateLog;
	}

	public void setDateLog(Date dateLog) {
		this.dateLog = dateLog;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getDetailLog() {
		return detailLog;
	}

	public void setDetailLog(String detailLog) {
		this.detailLog = detailLog;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	
}
