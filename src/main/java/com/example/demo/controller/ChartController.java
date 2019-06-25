package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Feedback;
import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectProgress;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskProgress;
import com.example.demo.service.FeedBackService;
import com.example.demo.service.ProjectProgressService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskProgressService;
import com.example.demo.service.TaskService;
import com.example.demo.validation.Util;

@Controller
public class ChartController {
	@Autowired
	private FeedBackService feebackService;
	@Autowired
	private TaskProgressService taskProgressService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectProgressService projectProgressService;

	@Autowired
	private TaskService taskService;

	@GetMapping("/home")
	public String homepage() {
		return "home";
	}

	@GetMapping("/task/{id}/displayBarGraph")
	public String taskBarGraph(@PathVariable("id") int id, Model model) {
		/*
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * String name = auth.getName(); // get logged in username
		 * model.addAttribute("username", name);
		 */
		Task task = taskService.findById(id);
		List<TaskProgress> workLogList = taskProgressService.findByTaskIDOrderByDateCreateAsc(id);
		List<Date> listDate;
		if (workLogList.size() > 0) {
			listDate = Util.getListDate(task.getDateStart(), task.getDeadlineDate(),
					workLogList.get(workLogList.size() - 1).getDateLog());
		} else {
			listDate = Util.getListDate(task.getDateStart(), task.getDeadlineDate(), new Date(0));
		}
		model.addAttribute("task", task);
		model.addAttribute("taskprogress", workLogList);
		model.addAttribute("listdatelog", Util.getLabelFromListDate(listDate));
		model.addAttribute("expect", Util.getListExpectProgress(task.getDateStart(), task.getDeadlineDate()));
		model.addAttribute("actual", Util.getListActualProgress(task.getDateStart(), workLogList));
		return "intror/barGraph";
	}

	@GetMapping("/displayPieChart")
	public String pieChart(Model model) {
		/*
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * String name = auth.getName(); // get logged in username
		 * model.addAttribute("username", name);
		 */

		List<Feedback> listFeedBack = feebackService.listfeedBack();
		int demVeryGood = 0;
		int demGood = 0;
		int demFaire = 0;
		int demPoor = 0;
		for (int i = 0; i < listFeedBack.size(); i++) {
			if (listFeedBack.get(i).getExperience().equals("excellent")) {
				demVeryGood++;
			} else {
				if (listFeedBack.get(i).getExperience().equals("good")) {
					demGood++;
				} else {
					if (listFeedBack.get(i).getExperience().equals("faire")) {
						demFaire++;
					} else {
						demPoor++;
					}
				}
			}
		}
		int sum = demFaire + demGood + demPoor + demVeryGood;
		System.out
				.println("veery good" + demVeryGood + " good" + demGood + "faire : " + demFaire + "poor : " + demPoor);
		model.addAttribute("excellent", demVeryGood * 100 / sum);
		model.addAttribute("good", demGood * 100 / sum);
		model.addAttribute("faire", demFaire * 100 / sum);
		model.addAttribute("poor", demPoor * 100 / sum);
		return "intror/pieChart";
	}

	@RequestMapping(value = "/task/{id}/taskprogress/save", method = RequestMethod.POST)
	public ModelAndView save(@PathVariable("id") int id, @ModelAttribute("taskprogress") TaskProgress taskprogress,
			RedirectAttributes redirect) {
		ModelAndView modelAndView = new ModelAndView();
		/*
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * String name = auth.getName(); // get logged in username
		 * modelAndView.addObject("username", name);
		 */
		
		taskProgressService.createTaskProgress(taskprogress);
		redirect.addFlashAttribute("notification", "bạn đã thực hiện ghi tiến độ thành công !");
		modelAndView.setViewName("redirect:/task/{id}/displayBarGraph");
		return modelAndView;
	}

	@RequestMapping(value = "/task/{id}/taskprogress/save", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		/*
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * String name = auth.getName(); // get logged in username
		 * modelAndView.addObject("username", name);
		 */
		Task task = taskService.findById(id);
		Set<Task> previousTasks = task.getPreviousTask();
		for (Task previousTask : previousTasks) {
			if(previousTask.getTaskState()<100) {
				modelAndView.setViewName("/error/403");
				return modelAndView;
			}
		}
		modelAndView.addObject("task", task);
		modelAndView.addObject("taskprogress", new TaskProgress());
		modelAndView.setViewName("progresstaskform");
		return modelAndView;
	}

	@GetMapping("/project/{id}/progress")
	public String projectBarGraph(@PathVariable("id") int id, Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////		String name = auth.getName(); // get logged in username
////		model.addAttribute("username", name);
		Project project = projectService.findByProjectId(id);
		List<ProjectProgress> workLogList = projectProgressService.findByProjectIDOrderByDateCreateAsc(id);
		List<Date> listDate;
		if (workLogList.size() > 0) {
			listDate = Util.getListDate(project.getStartDate(), project.getDeadlineDate(),
					workLogList.get(workLogList.size() - 1).getDateLog());
		} else {
			listDate = Util.getListDate(project.getStartDate(), project.getDeadlineDate(), new Date(0));
		}
		model.addAttribute("project", project);
		model.addAttribute("projectprogress", workLogList);
		model.addAttribute("listdatelog", Util.getLabelFromListDate(listDate));
		model.addAttribute("expect", Util.getListExpectProgress(project.getStartDate(), project.getDeadlineDate()));
		model.addAttribute("actual", Util.getListActualProjectProgress(project.getStartDate(), workLogList));
		return "intror/barGraphProject";
	}

}
