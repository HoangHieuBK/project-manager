package com.example.demo.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

/**
 *
 * @author lthung
 */
@Entity
@Table(name = "project")
@NamedQueries({ @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
		@NamedQuery(name = "Project.findByProjectId", query = "SELECT p FROM Project p WHERE p.projectId = :projectId"),
		@NamedQuery(name = "Project.findByProjectName", query = "SELECT p FROM Project p WHERE p.projectName = :projectName"),
		@NamedQuery(name = "Project.findByCreateDate", query = "SELECT p FROM Project p WHERE p.createDate = :createDate"),
		@NamedQuery(name = "Project.findByStartDate", query = "SELECT p FROM Project p WHERE p.startDate = :startDate"),
		@NamedQuery(name = "Project.findByDeadlineDate", query = "SELECT p FROM Project p WHERE p.deadlineDate = :deadlineDate"),
		@NamedQuery(name = "Project.findByFinishDate", query = "SELECT p FROM Project p WHERE p.finishDate = :finishDate"),
		@NamedQuery(name = "Project.findByDiscription", query = "SELECT p FROM Project p WHERE p.discription = :discription"),
		@NamedQuery(name = "Project.findByProjectState", query = "SELECT p FROM Project p WHERE p.projectState = :projectState"),
		@NamedQuery(name = "Project.findByProjectOutput", query = "SELECT p FROM Project p WHERE p.projectOutput = :projectOutput") })
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "project_id")
	private Integer projectId;
	@Basic(optional = false)
	@Column(name = "project_name")
	private String projectName;
	@Basic(optional = false)
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate;
	@Basic(optional = false)
	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@Basic(optional = false)
	@Column(name = "deadline_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deadlineDate;
	@Column(name = "finish_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Nullable
	private Date finishDate;
	@Column(name = "discription")
	private String discription;
	@Column(name = "project_state")
	private Integer projectState;
	@Column(name = "project_output")
	private String projectOutput;
	@ManyToMany(mappedBy = "staffProject")
	private Collection<Staff> staffProject;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
	private Collection<Task> task;

	public Project() {
	}

	public Project(Integer projectId) {
		this.projectId = projectId;
	}

	public Project(Integer projectId, String projectName, Date createDate, Date startDate, Date deadlineDate,
			Date finishDate) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.createDate = createDate;
		this.startDate = startDate;
		this.deadlineDate = deadlineDate;
		this.finishDate = finishDate;
	}

	public Collection<Staff> getStaffProject() {
		return staffProject;
	}

	public void setStaffProject(Collection<Staff> staffProject) {
		this.staffProject = staffProject;
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

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
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

	public Collection<Task> getTask() {
		return task;
	}

	public void setTask(Collection<Task> task) {
		this.task = task;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (projectId != null ? projectId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Project)) {
			return false;
		}
		Project other = (Project) object;
		if ((this.projectId == null && other.projectId != null)
				|| (this.projectId != null && !this.projectId.equals(other.projectId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gdfgdfgdfg.Project[ projectId=" + projectId + " ]";
	}

}
