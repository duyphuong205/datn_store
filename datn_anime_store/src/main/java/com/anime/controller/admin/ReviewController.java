package com.anime.controller.admin;

import org.springframework.stereotype.Controller;

import com.anime.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService;
	
	
}
