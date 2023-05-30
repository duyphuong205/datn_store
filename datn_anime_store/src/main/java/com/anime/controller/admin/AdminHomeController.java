package com.anime.controller.admin;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anime.service.StatisticalService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminHomeController {

	private final StatisticalService statisticalService;

	@SuppressWarnings("static-access")
	@GetMapping("/admin/index")
	public String doShowIndex(Model model,
			@RequestParam(value = "year", required = false, defaultValue = "2023") String year) {
		String sayHello = "";
		LocalTime time = LocalTime.now();
		if (time.isBefore(time.NOON)) {
			sayHello = "buổi sáng";
			model.addAttribute("sayHello", sayHello);
		} else {
			sayHello = "buổi chiều";
			model.addAttribute("sayHello", sayHello);
		}

		String[][] statisticalByYear = statisticalService.getTotalPriceLast6Months(year);
		List<String> years = statisticalService.getYears();

		model.addAttribute("totalQuantityProd", statisticalService.getTotalQuantityProd());
		model.addAttribute("totalUser", statisticalService.getTotalUser());
		model.addAttribute("totalPrice", statisticalService.getTotalPrice());
		model.addAttribute("statisticalByYear", statisticalByYear);
		model.addAttribute("years", years);
		model.addAttribute("currentYear", year);

		return "admin/index";
	}
}
