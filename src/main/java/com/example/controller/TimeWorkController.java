package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Events;
import com.example.demo.entity.Staff;
import com.example.demo.service.EventsService;
import com.example.demo.service.StaffService;

@Controller
public class TimeWorkController {
	@Autowired
	private EventsService eventsService;
	
	@Autowired
	private StaffService staffService;

	@GetMapping(value = "staff/{id}/timework")
	@ResponseBody
	public List<Events> getInfoTime(@PathVariable("id") int id,HttpSession request) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		List<Events> listEvents = null;
		try {
		 listEvents = eventsService.findByIdStaff(id);
		} catch (Exception e) {
			System.out.println("khoong co event nao !");
		}
		
		if (listEvents.equals(null)) {
			modelAndView.setViewName("error/403");
			return null;
		}
		
		 modelAndView.addObject("listevents",listEvents.get(0).getTitle());
		 System.out.println(listEvents.get(0));
		modelAndView.setViewName("timework/timework");
		System.out.println("session" + request.getId());
		return listEvents;
	}
	@GetMapping(value="staff/{id}/timeworks")
	public ModelAndView getString(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Events event = new Events();
		modelAndView.addObject("event",event);
		modelAndView.addObject("staff",id);
		modelAndView.addObject("staffs",staffService.findOne(id));
		modelAndView.setViewName("timework/timework");
		return modelAndView;
	} 
	
	@PostMapping(value="staff/{id}/timework/saveevent")
	public ModelAndView saveEvent(@ModelAttribute("event") Events event,@PathVariable("id") int id,RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("staff",id);
		Staff staff = staffService.findOne(id);
		event.setStaffId(staff);
		if(event.equals(null)) {
			modelAndView.setViewName("error/500");
		}
		eventsService.save(event);
		redirect.addFlashAttribute("notification", "Bạn Đã Thêm Thành Công Event!");
		modelAndView.setViewName("redirect:/staff/"+id+"/timeworks");
		return modelAndView;
	}
}
