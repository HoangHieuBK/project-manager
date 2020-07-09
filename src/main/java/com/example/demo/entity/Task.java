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

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Builder
@Data
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "task")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@ToString.Include
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "task_id")
	private Integer taskId;

	@Column(name = "task_idparent")
	private Integer taskIdparent;

	@Basic(optional = false)
	@Column(name = "task_name")
	private String taskName;

	@Basic(optional = false)
	@Column(name = "name_create")
	private String nameCreate;

	@Basic(optional = false)
	@Column(name = "date_create")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreate;

	@Basic(optional = false)
	@Column(name = "date_start")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;

	@Basic(optional = false)
	@Column(name = "deadline_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadlineDate;

	@Column(name = "finish_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Nullable
	private Date finishDate;

	@Column(name = "task_state")
	private Integer taskState;

	@Basic(optional = false)
	@Column(name = "discription")
	private String discription;

	@Column(name = "task_output")
	private String taskOutput;
	
	@JsonIgnore 
	@JoinColumn(name = "project_id", referencedColumnName = "project_id")
	@ManyToOne(optional = false)
	private Project projectId;

	@JsonIgnore 
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
	@ManyToOne()
	private Staff staffId;
	
	@JsonIgnore 
	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "task_relation", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = {
			@JoinColumn(name = "previous_task_id") })
	private Set<Task> previousTask = new HashSet<>();

	public Task() {
		this.taskState = 0;
	}
}