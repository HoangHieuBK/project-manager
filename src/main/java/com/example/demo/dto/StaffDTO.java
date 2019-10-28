package com.example.demo.dto;

import java.util.Collection;

import com.example.demo.entity.Account;
import com.example.demo.entity.Department;
import com.example.demo.entity.Project;

public class StaffDTO {

	private int staffId;
	
	private String name;
	
    private String gender;

    private String possition;

    private String skill;

    private String telephone;

    private String description;

    private Collection<Project> listProject;
    
    private String departmentName;

    private String accountName;
    
	public StaffDTO() {
		super();
	}

	

	public StaffDTO(int staffId, String name, String gender, String possition, String skill, String telephone,
			String description, Collection<Project> listProject, String departmentName, String accountName
			) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.gender = gender;
		this.possition = possition;
		this.skill = skill;
		this.telephone = telephone;
		this.description = description;
		this.listProject = listProject;
		this.departmentName = departmentName;
		this.accountName = accountName;
	}



	public StaffDTO(String name, String gender, String possition, String skill, String telephone, String description,
			Collection<Project> listProject, String departmentName, String accountName) {
		super();
		this.name = name;
		this.gender = gender;
		this.possition = possition;
		this.skill = skill;
		this.telephone = telephone;
		this.description = description; 
		this.listProject = listProject;
		this.departmentName = departmentName;
		this.accountName = accountName;
	}


	public int getStaffId() {
		return staffId;
	}


	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getPossition() {
		return possition;
	}


	public void setPossition(String possition) {
		this.possition = possition;
	}


	public String getSkill() {
		return skill;
	}


	public void setSkill(String skill) {
		this.skill = skill;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Collection<Project> getListProject() {
		return listProject;
	}


	public void setListProject(Collection<Project> listProject) {
		this.listProject = listProject;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


}
