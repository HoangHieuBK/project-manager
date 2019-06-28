package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account;
import com.example.demo.entity.Department;
import com.example.demo.entity.Staff;
import com.example.demo.service.AccountService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.StaffService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/staffs")
public class StaffController {
	@Autowired
	private StaffService staffService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProjectService projectService;

	@GetMapping("/staff")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("staffs", staffService.findAll());
		modelAndView.setViewName("liststaff");
		return modelAndView;
	}

	@GetMapping("/staff/add")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		List<Account> listAccount = accountService.findAllAccount();
		Map<Integer, String> accounts = new HashMap<>();
		listAccount.forEach(item -> accounts.put(item.getAccountId(), item.getAccountName()));
		modelAndView.addObject("accounts", accounts);

		List<Department> listDepartment = departmentService.findAllDepartment();
		Map<Integer, String> departments = new HashMap<>();
		listDepartment.forEach(item -> departments.put(item.getDepartmentId(), item.getDepartmentName()));
		modelAndView.addObject("departments", departments);
		
		modelAndView.addObject("staff", new Staff());
		modelAndView.setViewName("staffform");
		return modelAndView;
	}

	@GetMapping("/staff/{id}/edit")
	public ModelAndView edit(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("staff", staffService.findOne(id));
		modelAndView.addObject("departments", departmentService.findAllDepartment());
		modelAndView.addObject("accounts", accountService.findAllAccount());
		modelAndView.setViewName("staffform");
		return modelAndView;
	}

	@RequestMapping(value = "/staff/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("staff") Staff staff,RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
		staffService.save(staff);
		redirect.addFlashAttribute("successMessage", "Saved staff successfully!");
		modelAndView.setViewName("redirect:/staff");
		return modelAndView;
	}

	@GetMapping("/staff/{id}/delete")
	public String delete(@PathVariable int id,RedirectAttributes redirect) {
		staffService.delete(id);
		redirect.addFlashAttribute("successMessage", "Delete staff successfully!");
		return "redirect:/staff";
	}

	@GetMapping("/staff/search")
	public String search(@RequestParam("term") String term) {
		List<Staff> list = staffService.search(term);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("staffs", list);
		return "redirect:/staff";
	}

	@RequestMapping(value = "/staff/detail/{id}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable int id) {
		
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		if(staffService.findOne(id) == null) {
			modelAndView.setViewName("error/404");
		}else {
			modelAndView.addObject("staff", staffService.findOne(id));
			modelAndView.setViewName("detailstaff");
		}
		return modelAndView;
	}

	@GetMapping(value = "project/{id}/staff/{idstaff}/task")
	public ModelAndView getTask(@PathVariable int id, @PathVariable int idstaff) {
		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String name = auth.getName(); // get logged in username
//		modelAndView.addObject("username", name);
		
		modelAndView.addObject("project", projectService.getProjecByiD(id));
		modelAndView.addObject("staff", staffService.findOne(idstaff));
		modelAndView.addObject("tasks", staffService.getListTask(idstaff));
		modelAndView.setViewName("listtaskofstaff");
		return modelAndView;

	}
}
