package com.anime.service;

import com.anime.entity.User;
import com.anime.service.base.BaseService;

public interface UserService extends BaseService<User> {
	User register(User user);

	User getByUsername(String username);

	User getByEmail(String username);

	void updatePassword(String password, Long id);
}
