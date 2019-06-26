package com.example.demo.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Feedback;
import com.example.demo.service.FeedBackService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/feedbacks")
public class InforController {

	@Autowired
	private FeedBackService feedbackService;

	@Autowired
	public JavaMailSender emailSender;

	@GetMapping(value = "/")
	public List<Feedback> getAllWebFeedback() {
		List<Feedback> listFeedback = feedbackService.listfeedBack();
		return listFeedback;
	}

	@PostMapping(value = "/add")
	public ResponseEntity<Feedback> add( @RequestBody Feedback feedback) {
		return new ResponseEntity<Feedback>(feedbackService.saveFeedback(feedback), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public Feedback getFeedBackByID(@PathVariable("id") int id) {

		Optional<Feedback> feedbackData = feedbackService.getFeedBack(id);
		if(feedbackData.isPresent())
		{
			return feedbackData.get();
		}
		return null;
	}


}
