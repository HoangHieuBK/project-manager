package com.example.demo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Task;
import com.example.demo.repository.StaffRepo;
import com.example.demo.service.StaffService;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffRepo staffRepository;

	@Override
	public Staff save(Staff staff) {
		return staffRepository.save(staff);
	}

	@Override
	public List<Staff> findByUsername(String firstname) {
		return null;
	}

	@Override
	public List<Staff> findAll() {
		return staffRepository.findAll();
	}

	@Override
	public List<Staff> search(String term) {
		List<Staff> result = new LinkedList<>();
		List<Staff> listStaff = staffRepository.findAll();
		if (term == null || "".equals(term)) {
			result = listStaff;
		} else {
			for (Staff staff : listStaff) {
				if (staff.getName().toLowerCase().contains(term.toLowerCase())) {
					result.add(staff);
				}
			}
		}
		return result;
	}

	@Override
	public Staff findOne(int id) {
		Optional<Staff> staff = staffRepository.findById(id);
		if (staff.isPresent()) {
			return staff.get();
		}
		return null;
	}

	@Override
	public void delete(int id) {
		staffRepository.deleteById(id);
	}

	@Override
	public String getDerpatmentName(int id) {
		return null;
	}

	@Override
	public List<Task> getListTask(int staffId) {
		
		return staffRepository.fetchStaffP(staffId);
	}

	@Override
	public boolean existByStaffName(String staffName) {
		return staffRepository.existsByName(staffName);
	}

}
