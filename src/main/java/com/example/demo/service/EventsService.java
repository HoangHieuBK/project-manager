package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Events;

public interface EventsService {
	List<Events> findAll();

	List<Events> findByIdStaff(int idStaff);

	Events save(Events events);

	boolean deleteEvent(long id);
	
}
