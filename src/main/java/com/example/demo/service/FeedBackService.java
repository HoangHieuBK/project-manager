package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Feedback;

public interface FeedBackService {
	List<Feedback> listfeedBack();
	
	Feedback saveFeedback(Feedback feedback);
	
	Feedback getFeedBack(int id);
}
