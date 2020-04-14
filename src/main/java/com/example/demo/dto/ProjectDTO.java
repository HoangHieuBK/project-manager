package com.example.demo.dto;

import java.util.Collection;
import java.util.Date;

import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

	private Integer projectId;
	private String projectName;
	private Date createDate;
	private Date startDate;
	private Date deadlineDate;
	private Date finishDate;
	private String description;
	private Integer projectState;
	private String projectOutput;
	private Collection<String> staffsOfProject;
	private Collection<String> task;
	private Integer numberOfStaff;
	private Integer numberOfTask;

}
