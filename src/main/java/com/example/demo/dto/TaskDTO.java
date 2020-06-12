package com.example.demo.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.entity.Project;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

	private Integer taskId;
	private Integer taskIdParent;
	private String taskName;
	private String nameCreate;
	private Date dateCreate;
	private Date dateStart;
	private Date deadlineDate;
	private Date finishDate;
	private Integer taskState;
	private String discription;
	private String taskOutput;
	private Project project;
	private Integer staffId;
	private String staffName;
	private Set<String> previousTask = new HashSet<>();

}
