package com.anime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.anime.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final ProductService productService;
	
	@GetMapping("/index")
	public String doShowIndex(Model model) {
		model.addAttribute("topNewsProd", productService.getTop10NewsProd());
		model.addAttribute("topViewsProd", productService.getTop12ViewsProd());
		model.addAttribute("topSelledsProd", productService.getTop12ViewsProd());
		model.addAttribute("topDiscountsProd", productService.getTop12DiscountsProd());
		return "user/index";
	}

}
