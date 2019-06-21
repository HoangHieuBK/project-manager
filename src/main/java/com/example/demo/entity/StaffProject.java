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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lthung
 */
@Entity
@Table(name = "staff_project")
public class StaffProject implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer staffProjectId;
	@Basic(optional = false)
	@Column(name = "staff_id")
	private int staffId;
	@Basic(optional = false)
	@Column(name = "project_id")
	private int project_id;

	public Integer getStaffProjectId() {
		return staffProjectId;
	}

	public void setStaffProjectId(Integer staffProjectId) {
		this.staffProjectId = staffProjectId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public StaffProject(Integer staffProjectId, int staffId, int project_id) {
		super();
		this.staffProjectId = staffProjectId;
		this.staffId = staffId;
		this.project_id = project_id;
	}

}
