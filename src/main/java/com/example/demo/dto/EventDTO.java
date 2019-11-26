package com.example.demo.dto;

import java.util.Date;

import com.example.demo.entity.Staff;

public class EventDTO {
	
	private Long id;
	private String title;
	private String description;
	private Date start;
	private Date end;
	private Staff staff;
	
	
	public EventDTO() {
		super();
	}


	public EventDTO(String title, Date start, Date end) {
		super();
		this.title = title;
		this.start = start;
		this.end = end;
	}


	public EventDTO(String title, String description, Date start, Date end, Staff staff) {
		super();
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		this.staff = staff;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
	}


	public Staff getStaff() {
		return staff;
	}


	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	
	
}
