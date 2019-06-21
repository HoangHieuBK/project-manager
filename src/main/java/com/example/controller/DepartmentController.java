package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping(value = { "/department" })
	public ModelAndView getAllDepartment() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		modelAndView.addObject("departments", departmentService.findAllDepartment());
		modelAndView.setViewName("listdepartment");
		return modelAndView;
	}

	@GetMapping("/department/add")
	public String add(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		model.addAttribute("username", name);
//		staff.setDepartmentId(departmentId);
		model.addAttribute("department", new Department());

		return "departmentform";
	}

	@GetMapping("/department/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		model.addAttribute("username", name);
		Department department = departmentService.findDepartmentById(id);
		department.setCheck(false);
		model.addAttribute("department",department );
		return "departmentform";
	}

	@RequestMapping(value = "/department/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("department") Department department,RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
		if(departmentService.saveDepartment(department)== null) {
			modelAndView.setViewName("redirect:/error/400");
			return modelAndView;
		};
		redirect.addFlashAttribute("notification", "Bạn Lưu Thành Công Department !");
		modelAndView.setViewName("redirect:/department");
		return modelAndView;
	}

	@GetMapping("/department/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		departmentService.deleteDepartment(id);
		redirect.addFlashAttribute("notification", "Bạn Xóa Thành Công Department !");
		return "redirect:/department";
	}

//	@GetMapping("/project/search")
//	public String search(@RequestParam("key") String key) {
//		List<Project> list = projectService.;
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("staffs", list);
//		return "redirect:/staff";
//	}

	@RequestMapping(value = "/department/detail/{id}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
	
		modelAndView.addObject("department", departmentService.findDepartmentById(id));
		modelAndView.setViewName("detaildepartment");
		return modelAndView;
	}

	
}
