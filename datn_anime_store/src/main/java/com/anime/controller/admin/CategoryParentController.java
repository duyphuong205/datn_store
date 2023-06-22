package com.anime.controller.admin;

import com.anime.entity.CategoryParent;
import com.anime.mapping.CategoryParentMapping;
import com.anime.service.CategoryParentService;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = CategoryParentMapping.ADMIN)
public class CategoryParentController {
	@Autowired
	private CategoryParentService categoryParentService;

	@RequestMapping(value = CategoryParentMapping.CATEGORY_PARENT_PAGE, method = RequestMethod.GET)
	public String doShowView(Model model, @RequestParam(name = "pageNum") Optional pageNum) {
		int page = !pageNum.isEmpty() ? Integer.parseInt(pageNum.get().toString()) - 1 : 0;
		int pageSize = 2;
		model.addAttribute("pageSize", pageSize);
		Page<CategoryParent> categoryParents = categoryParentService.getByIsActive(PageRequest.of(page, pageSize));
		model.addAttribute("currentPage", page + 1);
		model.addAttribute("totalPages", categoryParents.getTotalPages());
		model.addAttribute("totalItems", categoryParents.getTotalElements());
		model.addAttribute("categoryParents", categoryParents.getContent());
		model.addAttribute("categoryParentForm", new CategoryParent());
		return CategoryParentMapping.ADMIN_CATEGORY_PARENT;
	}

	@RequestMapping(value = CategoryParentMapping.CREATE, method = RequestMethod.POST)
	public ModelAndView doCreate(@Valid @ModelAttribute("categoryParentForm") CategoryParent categoryParent,
								 BindingResult result) {
		if (result.hasErrors()) { return new ModelAndView(CategoryParentMapping.REDIRECT_URL); }
		categoryParentService.create(categoryParent);
		return new ModelAndView(CategoryParentMapping.REDIRECT_URL);
	}

	@RequestMapping(value =CategoryParentMapping.DELETE, method = RequestMethod.GET)
	public ModelAndView doDelete(@PathVariable Long id) {
		categoryParentService.delete(id);
		return new ModelAndView(CategoryParentMapping.REDIRECT_URL);
	}
}
