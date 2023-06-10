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
import org.springframework.web.multipart.MultipartFile;

import com.anime.entity.User;
import com.anime.service.FileService;
import com.anime.service.UserService;
import com.anime.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerController {
	private final UserService userService;

	private final FileService fileService;

	private final SessionUtil session;

	@GetMapping("/admin/customer")
	public String doShowView(Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "8") int pageSize) {
		List<User> users = new ArrayList<>();
		try {
			Page<User> pageUsers = userService.getListCustomer(PageRequest.of(pageNumber - 1, pageSize));
			users = pageUsers.getContent();
			model.addAttribute("totalPages", pageUsers.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("users", users);
		return "admin/customer";
	}

	@PostMapping("/admin/customer/add")
	public String doCreate(User userReq, @RequestParam("imageName") MultipartFile imageName) {
		try {
			String imageUrl = "";
			if (imageName.getSize() == 0 || imageName == null || imageName.isEmpty()) {
				imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png";
			} else {
				imageUrl = fileService.uploadFile(imageName);
			}
			userReq.setAvatarUrl(imageUrl);
			userService.createCustomer(userReq);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/admin/customer";
	}

	@PostMapping("/admin/customer/update")
	public String doUpdate(User userReq, @RequestParam("imageName") MultipartFile imageName) {
		try {
			String imageUrl = "";
			if (imageName.getSize() == 0 || imageName == null || imageName.isEmpty()) {
				imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png";
			} else {
				imageUrl = fileService.uploadFile(imageName);
			}
			userReq.setAvatarUrl(imageUrl);
			userService.update(userReq);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/admin/customer";
	}

	@GetMapping("/admin/customer/delete/{id}")
	public String doDelete(@PathVariable("id") Long id) {
		try {
			userService.deleteLogical(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/admin/customer";
	}

	@ResponseBody
	@GetMapping("/admin/customer/edit/{id}")
	public User doEdit(@PathVariable("id") Long id) {
		return userService.findById(id);
	}

	@GetMapping("/admin/customer/search")
	public String doSearch(Model model, @RequestParam(value = "keyword", required = false) Optional<String> kw,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "8") int pageSize) {
		List<User> users = new ArrayList<>();
		try {
			String keyword = kw.orElse(session.getAttribute("keyword"));
			session.setAttribute("keyword", keyword);
			Page<User> pageUsers = userService.getListSearchCustomer(keyword, PageRequest.of(pageNumber - 1, pageSize));
			users = pageUsers.getContent();
			model.addAttribute("totalPages", pageUsers.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("users", users);
		return "admin/customer";
	}
}
