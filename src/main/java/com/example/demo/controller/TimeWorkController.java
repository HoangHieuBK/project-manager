package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.EventDTO;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.entity.Events;
import com.example.demo.entity.Staff;
import com.example.demo.service.EventsService;
import com.example.demo.service.StaffService;

@CrossOrigin(origins = "*")
@RestController
public class TimeWorkController {
    @Autowired
    private EventsService eventsService;

    @Autowired
    private StaffService staffService;

    @GetMapping(value = "staffs/{id}/timeworks")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getInfoTime(@PathVariable("id") int id) {
        List<Events> listEvents = new ArrayList<Events>();
        try {
            listEvents = eventsService.findByIdStaff(id);
        } catch (Exception e) {
            System.out.println("không có event nào !");
        }
        if (listEvents.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Nhân viên chưa có lịch trình làm việc!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listEvents, HttpStatus.OK);
    }

    @PostMapping(value = "staffs/{id}/addTimeworks")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO eventDTO, @PathVariable("id") int id) {
        if (eventDTO.getEnd().before(eventDTO.getStart())) {
            return new ResponseEntity<>(new ResponseMessage("Ngày kết thúc không thể trước ngày bắt đầu!"),
                    HttpStatus.BAD_REQUEST);
        }
        Events event = Events.builder()
                      .title(eventDTO.getTitle())
                      .description(eventDTO.getDescription())
                      .start(eventDTO.getStart())
                      .end(eventDTO.getEnd())
                      .build();

        Staff staff = staffService.findOne(id);
        event.setStaffId(staff);
        eventsService.save(event);

        return new ResponseEntity<>(new ResponseMessage("Tạo sự kiện thành công!"), HttpStatus.OK);
    }
}
