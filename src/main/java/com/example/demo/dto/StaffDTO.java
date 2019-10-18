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
    
    private Department department;

    private Account account;

	public StaffDTO() {
		super();
	}

	public StaffDTO(String name, String gender, String possition, String skill, String telephone, String description,
			Collection<Project> listProject, Department department, Account account) {
		super();
		this.name = name;
		this.gender = gender;
		this.possition = possition;
		this.skill = skill;
		this.telephone = telephone;
		this.description = description;
		this.listProject = listProject;
		this.department = department;
		this.account = account;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


}
