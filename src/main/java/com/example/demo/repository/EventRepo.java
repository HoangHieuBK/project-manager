package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Events;

@Repository
public interface EventRepo extends JpaRepository<Events, Long>{
	
	@Query("select e from Events e where e.staffId.staffId= :staffId")
	public List<Events> getEventByStaff(int staffId);
}
