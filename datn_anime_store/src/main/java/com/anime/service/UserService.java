package com.anime.service;

import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.User;
import com.anime.security.Account;
import com.anime.service.base.BaseService;

public interface UserService extends BaseService<User> {
	User register(User user);

	User getByUsername(String username);

	User getByEmail(String username);

	void updatePassword(String password, Long id);

	Page<User> getListCustomer(Pageable pageable);

	Page<User> getListSearchCustomer(String keyword, Pageable pageable);

	User createCustomer(User user);

	void deleteLogical(Long id) throws SQLException;

	User getByUsernameOrEmail(String username);

	public void setAccount(Account account);

//	public User createFromSocial(OAuth2User socialUser);
}
