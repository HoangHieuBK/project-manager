package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Feedback;
import com.example.demo.repository.FeedbackRepo;
import com.example.demo.service.FeedBackService;
@Service
public class FeedBackServiceImpl implements FeedBackService{
	
	@Autowired
	private FeedbackRepo feedbackRepo;

	@Override
	public List<Feedback> listfeedBack() {
		// TODO Auto-generated method stub
		return feedbackRepo.findAll();
	}

	@Override
	public Feedback saveFeedback(Feedback feedback) {
		// TODO Auto-generated method stub
		return feedbackRepo.save(feedback);
	}

	@Override
	public Feedback getFeedBack(int id) {
		// TODO Auto-generated method stub
		return feedbackRepo.getOne(id);
	}

}
