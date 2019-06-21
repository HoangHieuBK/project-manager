package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "progress_task")
public class TaskProgress implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "progressId")
  private Long progressId;

  @Basic(optional = false)
  @Column(name = "datelog")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date dateLog;

  @Column(name = "progress")
  private int progress;

  @Column(name = "detail_log")
  private String detailLog;

  @JoinColumn(name = "task_id", referencedColumnName = "task_id")
  @ManyToOne(optional = false)
  private Task taskId;

  public TaskProgress() {
    super();
  }

  public TaskProgress(Long progressId, Date dateLog, int progress, String detailLog, Task taskId) {
    super();
    this.progressId = progressId;
    this.dateLog = dateLog;
    this.progress = progress;
    this.detailLog = detailLog;
    this.taskId = taskId;
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

  public Task getTaskId() {
    return taskId;
  }

  public void setTaskId(Task taskId) {
    this.taskId = taskId;
  }
}
