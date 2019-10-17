package com.example.demo.dto;

public class DepartmentDTO {

	private String departmentName;
	
	private String managerName;
	
	private String description;

	public DepartmentDTO() {
		super();
	}

	public DepartmentDTO(String departmentName, String managerName, String description) {
		super();
		this.departmentName = departmentName;
		this.managerName = managerName;
		this.description = description;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
