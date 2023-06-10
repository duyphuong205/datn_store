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

import com.anime.entity.UnitType;
import com.anime.service.UnitTypeService;
import com.anime.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UnitTypeController {

	private final UnitTypeService unitTypeService;

	private final SessionUtil session;

	@GetMapping("/admin/unit-type")
	public String doShowView(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") int pageSize) {
		List<UnitType> unitTypes = new ArrayList<>();
		try {
			Page<UnitType> pageUnitTypes = unitTypeService.getByIsActive(PageRequest.of(pageNumber - 1, pageSize));
			unitTypes = pageUnitTypes.getContent();
			model.addAttribute("totalPages", pageUnitTypes.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("unitTypes", unitTypes);
		return "admin/unit-type";
	}

	@PostMapping("/admin/unit-type/add")
	public String doAdd(UnitType unitType) {
		try {
			unitTypeService.create(unitType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/unit-type";
	}

	@ResponseBody
	@GetMapping("/admin/unit-type/edit/{id}")
	public UnitType doEdit(@PathVariable("id") Long id) {
		return unitTypeService.findById(id);
	}

	@PostMapping("/admin/unit-type/update")
	public String doUpdate(UnitType unitType) {
		try {
			unitTypeService.update(unitType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/unit-type";
	}

	@GetMapping("/admin/unit-type/delete/{id}")
	public String doDelete(@PathVariable("id") Long id) {
		try {
			unitTypeService.deleteLogical(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/unit-type";
	}

	@GetMapping("/admin/unit-type/search")
	public String doSearch(Model model, @RequestParam(value = "keyword", required = false) Optional<String> kw,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") int pageSize) {
		List<UnitType> unitTypes = new ArrayList<>();
		try {
			String keyword = kw.orElse(session.getAttribute("keyword"));
			session.setAttribute("keyword", keyword);
			Page<UnitType> pageUnitTypes = unitTypeService.getByKeyword(keyword,
					PageRequest.of(pageNumber - 1, pageSize));
			unitTypes = pageUnitTypes.getContent();
			model.addAttribute("totalPages", pageUnitTypes.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("unitTypes", unitTypes);
		return "admin/unit-type";
	}
}
