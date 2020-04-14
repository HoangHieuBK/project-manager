package com.example.demo.dto;

import java.util.Collection;
import java.util.Set;

import com.example.demo.entity.Account;
import com.example.demo.entity.Department;
import com.example.demo.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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


}
