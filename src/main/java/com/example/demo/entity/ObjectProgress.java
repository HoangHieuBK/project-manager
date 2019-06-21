package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class ObjectProgress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "progressId")
	private Long progressId;
	
	@Column(name = "datelog")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateLog;

	@Column(name = "progress")
	private int progress;

	@Column(name = "detail_log")
	private String detailLog;

	private int confirm;
	
	
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

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

}
