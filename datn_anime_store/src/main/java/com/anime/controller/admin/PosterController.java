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

import com.anime.entity.Poster;
import com.anime.service.PosterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PosterController {

	private final PosterService posterService;

	@GetMapping("/admin/poster")
	public String doShowView(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") int pageSize) {
		List<Poster> posters = new ArrayList<>();
		try {
			Page<Poster> pagePosters = posterService.getByIsActive(PageRequest.of(pageNumber - 1, pageSize));
			posters = pagePosters.getContent();
			model.addAttribute("totalPages", pagePosters.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("posters", posters);
		return "admin/poster";
	}

	@PostMapping("/admin/poster/add")
	public String doAdd(Poster poster) {
		try {
			posterService.create(poster);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/poster";
	}

	@ResponseBody
	@GetMapping("/admin/poster/edit/{id}")
	public Poster doEdit(@PathVariable("id") Long id) {
		return posterService.findById(id);
	}

	@PostMapping("/admin/poster/update")
	public String doUpdate(Poster poster) {
		try {
			posterService.update(poster);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/poster";
	}

	@GetMapping("/admin/poster/delete/{id}")
	public String doDelete(@PathVariable("id") Long id) {
		try {
			posterService.deleteLogical(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/poster";
	}
}
