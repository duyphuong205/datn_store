package com.anime.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anime.constants.ActiveConstant;
import com.anime.entity.Review;
import com.anime.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@GetMapping("/admin/review")
	public String doShowView(Model model) {
		model.addAttribute("reviewRefuseds", reviewService.getReviewRefused(ActiveConstant.DISABLE));
		model.addAttribute("reviewAccepteds", reviewService.getReviewAccepted(ActiveConstant.ENABLE));
		return "admin/review";
	}

	@ResponseBody
	@GetMapping("/admin/review/edit/{id}")
	public Review doEdit(@PathVariable("id") Long id) {
		return reviewService.findById(id);
	}

	@PostMapping("/admin/review/update")
	public String doUpdate(@RequestParam("id") Long id, @RequestParam("isActive") Boolean isActive) {
		try {
			Review review = reviewService.findById(id);
			review.setIsActive(isActive);
			
			reviewService.update(review);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/review";
	}
}
