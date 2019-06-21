package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Integer> {
//   List<Staff> findByFirstname(String firstname);
	@Query("select t from Task t where t.staffId.staffId= :staffId")
	List<Task> fetchStaffP(@Param("staffId") int staffId);
	
}
