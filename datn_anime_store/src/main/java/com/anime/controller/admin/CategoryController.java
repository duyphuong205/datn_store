package com.anime.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anime.entity.Category;
import com.anime.service.CategoryService;
import com.anime.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	private final SessionUtil session;

	@GetMapping("/admin/category")
	public String doShowView(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") int pageSize) {
		List<Category> categories = new ArrayList<>();
		try {
			Page<Category> pageCategories = categoryService.getByIsActice(PageRequest.of(pageNumber - 1, pageSize));
			categories = pageCategories.getContent();
			model.addAttribute("totalPages", pageCategories.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("categories", categories);
		return "admin/category";
	}

	@PostMapping("/admin/category/add")
	public String doAdd(Category category) {
		try {
			categoryService.create(category);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/category";
	}

	@ResponseBody
	@GetMapping("/admin/category/edit/{id}")
	public Category doEdit(@PathVariable("id") Long id) {
		return categoryService.findById(id);
	}

	@PostMapping("/admin/category/update")
	public String doUpdate(Category category) {
		try {
			categoryService.update(category);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/admin/category/delete/{id}")
	public String doDelete(@PathVariable("id") Long id) {
		try {
			categoryService.deleteLogical(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/admin/category/search")
	public String doSearch(Model model, 
			@RequestParam(value = "keyword", required = false) Optional<String> kw,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") int pageSize) {
		List<Category> categories = new ArrayList<>();
		try {
			String keyword = kw.orElse(session.getAttribute("keyword"));
			session.setAttribute("keyword", keyword);
			Page<Category> pageCategories = categoryService.getByKeyword(keyword,
					PageRequest.of(pageNumber - 1, pageSize));
			categories = pageCategories.getContent();
			model.addAttribute("totalPages", pageCategories.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("categories", categories);
		return "admin/category";
	}
}
