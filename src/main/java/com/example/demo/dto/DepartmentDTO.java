package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

	private int departmentId;
	
	private String departmentName;
	
	private String managerName;
	
	private String description;

}
