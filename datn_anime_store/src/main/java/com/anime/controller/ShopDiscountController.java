package com.anime.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anime.entity.Product;
import com.anime.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShopDiscountController {
	private final ProductService productService;

	@GetMapping("/shop-discount")
	public String doShowShopDiscount(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "8") int pageSize, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			Page<Product> pageProducts = productService.getByDiscount(PageRequest.of(pageNum - 1, pageSize));
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("products", products);
		return "/user/shop-discount";
	}
}
