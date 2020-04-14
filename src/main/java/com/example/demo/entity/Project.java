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

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Builder
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "project")
@JsonIgnoreProperties(value = { "staffProject" })
@NamedQueries({ @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
		@NamedQuery(name = "Project.findByProjectId", query = "SELECT p FROM Project p WHERE p.projectId = :projectId"),
		@NamedQuery(name = "Project.findByProjectName", query = "SELECT p FROM Project p WHERE p.projectName = :projectName"),
		@NamedQuery(name = "Project.findByCreateDate", query = "SELECT p FROM Project p WHERE p.createDate = :createDate"),
		@NamedQuery(name = "Project.findByStartDate", query = "SELECT p FROM Project p WHERE p.startDate = :startDate"),
		@NamedQuery(name = "Project.findByDeadlineDate", query = "SELECT p FROM Project p WHERE p.deadlineDate = :deadlineDate"),
		@NamedQuery(name = "Project.findByFinishDate", query = "SELECT p FROM Project p WHERE p.finishDate = :finishDate"),
		@NamedQuery(name = "Project.findBydescription", query = "SELECT p FROM Project p WHERE p.description = :description"),
		@NamedQuery(name = "Project.findByProjectState", query = "SELECT p FROM Project p WHERE p.projectState = :projectState"),
		@NamedQuery(name = "Project.findByProjectOutput", query = "SELECT p FROM Project p WHERE p.projectOutput = :projectOutput") })
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@ToString.Include
	@EqualsAndHashCode.Include
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@Basic(optional = false)
	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@Basic(optional = false)
	@Column(name = "deadline_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadlineDate;

	@Column(name = "finish_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private Date finishDate;

	@Column(name = "description")
	private String description;

	@Column(name = "project_state")
	private Integer projectState;

	@Column(name = "project_output")
	private String projectOutput;

	@ManyToMany(mappedBy = "staffProject")
	private Collection<Staff> staffProject;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
	private Collection<Task> task;

}
