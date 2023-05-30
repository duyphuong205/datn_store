package com.anime.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anime.entity.CategoryParent;
import com.anime.service.CategoryParentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryParentController {

	private final CategoryParentService categoryParentService;

	@GetMapping("/admin/category-parent")
	public String doShowView(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") int pageSize) {
		List<CategoryParent> categoryParents = new ArrayList<>();
		try {
			Page<CategoryParent> pageCategoryParents = categoryParentService
					.getByIsActice(PageRequest.of(pageNumber - 1, pageSize));
			categoryParents = pageCategoryParents.getContent();
			model.addAttribute("totalPages", pageCategoryParents.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("categoryParents", categoryParents);
		return "admin/category-parent";
	}

	@PostMapping("/admin/category-parent/add")
	public String doAdd(CategoryParent categoryParent) {
		try {
			categoryParentService.create(categoryParent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/category-parent";
	}

	@ResponseBody
	@GetMapping("/admin/category-parent/edit/{id}")
	public CategoryParent doEdit(@PathVariable("id") Long id) {
		return categoryParentService.findById(id);
	}

	@PostMapping("/admin/category-parent/update")
	public String doUpdate(CategoryParent categoryParent) {
		try {
			categoryParentService.update(categoryParent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/category-parent";
	}

	@GetMapping("/admin/category-parent/delete/{id}")
	public String doDelete(@PathVariable("id") Long id) {
		try {
			categoryParentService.deleteLogical(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/category-parent";
	}
}
