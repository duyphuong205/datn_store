package com.anime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
	
	@GetMapping("/login")
	public String doShowLogin() {
		return "user/login";
	}
	
	@GetMapping("/register")
	public String doShowRegister() {
		return "user/register";
	}
}
