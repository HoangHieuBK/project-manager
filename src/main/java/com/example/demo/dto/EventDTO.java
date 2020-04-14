package com.example.demo.dto;

import java.util.Date;

import com.example.demo.entity.Staff;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventDTO {
	private Long id;
	private String title;
	private String description;
	private Date start;
	private Date end;
	private Staff staff;

}
