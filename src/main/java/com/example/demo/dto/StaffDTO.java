package com.example.demo.dto;

import java.util.Collection;
import java.util.Set;

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

	private String departmentName;

	private String accountName;

	private String email;

	private String managerName ;

    private Set<String> role;

	public StaffDTO() {
		super();
	}

	public StaffDTO(int staffId, String name, String gender, String possition, String skill, String telephone,
			String description, String departmentName, String accountName) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.gender = gender;
		this.possition = possition;
		this.skill = skill;
		this.telephone = telephone;
		this.description = description;
		this.departmentName = departmentName;
		this.accountName = accountName;
	}

	public StaffDTO(String name, String gender, String possition, String skill, String telephone, String description,
			String departmentName, String accountName) {
		super();
		this.name = name;
		this.gender = gender;
		this.possition = possition;
		this.skill = skill;
		this.telephone = telephone;
		this.description = description;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}


	
}
