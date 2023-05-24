package com.anime.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anime.entity.User;
import com.anime.service.FileService;
import com.anime.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {

	private final UserService userService;

	private final FileService fileService;

	@GetMapping("/login")
	public String doShowLogin() {
		return "user/login";
	}
	
	@GetMapping("/login/error")
	public String doLoginError(Model model) {
		model.addAttribute("message", "Tên người dùng hoặc mật khẩu không chính xác!");
		return "user/login";
	}

	@GetMapping("/register")
	public String doShowRegister(Model model) {
		model.addAttribute("userReq", new User());
		return "user/register";
	}

	@PostMapping("/register")
	public String doRegister(@Valid @ModelAttribute("userReq") User userReq, Errors errors, Model model,
			@RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("confirmPassword") String confirmPassword) {
		try {
			if (errors.hasErrors()) {
				return "user/register";
			} else {
				String imageUrl = "";
				if (fileImage.getSize() == 0 || fileImage == null || fileImage.isEmpty()) {
					imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png";
				} else {
					imageUrl = fileService.uploadFile(fileImage);
				}
				if (userService.getByUsername(userReq.getUsername()) != null) {
					model.addAttribute("existsUsername", "Tên người dùng đã tồn tại!");
					return "user/register";
				}
				if (userService.getByEmail(userReq.getEmail()) != null) {
					model.addAttribute("existsEmail", "Email người dùng đã tồn tại!");
					return "user/register";
				}
				if (confirmPassword == null || confirmPassword.equals("")) {
					model.addAttribute("confirmPasswordNull", "Vui lòng nhập lại mật khẩu!");
					return "user/register";
				}
				if (!confirmPassword.equalsIgnoreCase(userReq.getPassword())) {
					model.addAttribute("notMatchPass", "Mật khẩu không khớp nhau!");
					return "user/register";
				}
				userReq.setAvatarUrl(imageUrl);
				userService.register(userReq);
				return "redirect:/login";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "/user/register";
		}
	}
}
