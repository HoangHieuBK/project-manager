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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "progress_project")
public class ProjectProgress implements Serializable{

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "progressId")
  private Long progressId;

  @Column(name = "datelog")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date dateLog;

  @Column(name = "progress")
  private int progress;

  @Column(name = "detail_log")
  private String detailLog;
  
  @JsonIgnore
  @JoinColumn(name = "project_id", referencedColumnName = "project_id")
  @ManyToOne(optional = false)
  private Project projectId;

  public ProjectProgress() {
    this.progress = 0;
  }
}
