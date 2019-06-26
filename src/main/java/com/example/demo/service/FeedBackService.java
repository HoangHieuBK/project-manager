package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Feedback;

public interface FeedBackService {
	List<Feedback> listfeedBack();
	
	Feedback saveFeedback(Feedback feedback);
	
	Optional<Feedback> getFeedBack(int id);
}
