package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Events;
import com.example.demo.repository.EventRepo;
import com.example.demo.service.EventsService;

@Service
public class EventsServiceImpl implements EventsService{
	@Autowired
	private EventRepo eventsRepo;

	@Override
	public List<Events> findAll() {
		// TODO Auto-generated method stub
		return eventsRepo.findAll();
	}

	@Override
	public List<Events> findByIdStaff(int idStaff) {
		// TODO Auto-generated method stub
		if(eventsRepo.getEventByStaff(idStaff).isEmpty()) {
			return null;
		}
		
		return eventsRepo.getEventByStaff(idStaff);
	}

	@Override
	public Events save(Events events) {
		// TODO Auto-generated method stub
		return eventsRepo.save(events);
	}

	@Override
	public boolean deleteEvent(long id) {
		if(eventsRepo.findById(id).equals(null)) {
			return false;
		}
		eventsRepo.deleteById(id);
		return true;
	}
}
