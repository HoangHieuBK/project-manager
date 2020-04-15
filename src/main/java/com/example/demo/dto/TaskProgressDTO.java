package com.example.demo.dto;

import java.util.Date;

import com.example.demo.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskProgressDTO {
	private Long progressId;
	private Date dateLog;
	private int progress;
	private String detailLog;
	private Task task;

}
