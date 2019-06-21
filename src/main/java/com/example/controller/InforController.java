package com.example.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Feedback;
import com.example.demo.service.FeedBackService;

@Controller
public class InforController {
	@Autowired
	private FeedBackService feedback;
	@Autowired
	public JavaMailSender emailSender;

	@GetMapping(value = "/aboutteam")
	public ModelAndView getInforTeam() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		modelAndView.setViewName("intror/aboutteam");
		return modelAndView;
	}

	@GetMapping(value = "/aboutapp")
	public ModelAndView getInforWeb() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		modelAndView.setViewName("intror/aboutapp");
		return modelAndView;
	}

	@GetMapping(value = "/feedback/add")
	public ModelAndView getInforWebFeedback() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		modelAndView.addObject("feedback", new Feedback());
		modelAndView.setViewName("intror/feedback");
		return modelAndView;
	}

	@RequestMapping(value = "/feedback/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("feedback") Feedback feedbacks) {

		feedback.saveFeedback(feedbacks);

		return new ModelAndView("redirect:/thankyou");
	}

	@GetMapping(value = "/getfeedback")
	public ModelAndView getAllWebFeedback() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		modelAndView.addObject("feedbacks", feedback.listfeedBack());
		modelAndView.setViewName("intror/listfeedback");
		return modelAndView;
	}

	@GetMapping(value = "/thankyou")
	public String thank() {
		return "intror/thankyou";
	}

	@GetMapping(value = "/getfeedback/{id}/feedack")
	public ModelAndView feedBack(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		modelAndView.addObject("feedback", feedback.getFeedBack(id));
		modelAndView.setViewName("intror/quickemail");
		return modelAndView;
	}

	@PostMapping(value = "/getfeedback/{id}/feedack")
	public ModelAndView sendreponsefeedBack(@PathVariable("id") int id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username
		modelAndView.addObject("username", name);
		
		modelAndView.addObject("feedback", feedback.getFeedBack(id));
		Feedback feedbackreponse = feedback.getFeedBack(id);
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(feedbackreponse.getEmail());
		message.setSubject("Thank you your feedback !");
		message.setText("Hello, " + feedbackreponse.getName() + ", \r\n"
				+ "Chúng tôi sẽ ghi nhận ý kiến của bạn và nâng cấp phần mềm tốt hơn trong thời gian tới !");

		// Send Message!
		this.emailSender.send(message);
		return new ModelAndView("redirect:/getfeedback");
	}
	
	  @RequestMapping(value = "/username", method = RequestMethod.GET)
	    @ResponseBody
	    public String currentUserNameSimple(HttpServletRequest request) {
	        Principal principal = request.getUserPrincipal();
	        System.out.println(principal.getName());
	        return principal.getName();
	    }
	 
}
