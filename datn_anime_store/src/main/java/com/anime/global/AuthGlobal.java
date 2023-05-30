package com.anime.global;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.anime.entity.User;
import com.anime.service.UserService;

import lombok.RequiredArgsConstructor;

@Service("AuthGlobal")
@RequiredArgsConstructor
public class AuthGlobal {
	private final UserService userService;

	private final HttpServletRequest request;

	public User getInforUser() {
		String username = request.getRemoteUser();
		return userService.getByUsername(username);
	}
}
