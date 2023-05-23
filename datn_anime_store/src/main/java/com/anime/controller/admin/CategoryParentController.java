package com.anime.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryParentController {
	@GetMapping("/admin/category-parent")
	public String doShowView() {
		return "admin/category-parent";
	}
}
