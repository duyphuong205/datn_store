package com.anime.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anime.constants.ActiveConstant;
import com.anime.constants.HashPasswordConstant;
import com.anime.constants.RoleConstant;
import com.anime.entity.Role;
import com.anime.entity.User;
import com.anime.entity.UserRole;
import com.anime.repo.UserRepo;
import com.anime.security.Account;
import com.anime.service.RoleService;
import com.anime.service.UserRoleService;
import com.anime.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class UserServiceImpl implements UserService {
	private final UserRepo userRepo;

	private final RoleService roleService;

	private final UserRoleService userRoleService;

	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepo.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public User create(User entity) {
		return userRepo.save(entity);
	}

	@Override
	public User update(User entity) {
		return userRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		userRepo.deleteById(id);
	}

	@Override
	public User register(User user) {
		try {
			user.setPassword(HashPasswordConstant.ENCODER.encode(user.getPassword()));
			userRepo.save(user);
			Role role = roleService.getByName(RoleConstant.ROLE_CUSTOMER);
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			userRoleService.create(userRole);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}

	@Override
	public User getByUsername(String username) {
		return userRepo.findByUsernameAndIsActive(username, ActiveConstant.ENABLE);
	}

	@Override
	public User getByEmail(String username) {
		return userRepo.findByEmailAndIsActive(username, ActiveConstant.ENABLE);
	}

	@Override
	public void updatePassword(String password, Long id) {
		userRepo.updatePassword(password, id);
	}

	@Override
	public Page<User> getListCustomer(Pageable pageable) {
		return userRepo.findAllCustomer(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public User createCustomer(User user) {
		try {
			user.setPassword(HashPasswordConstant.ENCODER.encode(user.getPassword()));
			userRepo.save(user);
			Role role = roleService.getByName(RoleConstant.ROLE_CUSTOMER);
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			userRoleService.create(userRole);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}

	@Override
	public void deleteLogical(Long id) throws SQLException {
		userRepo.deleteLogical(ActiveConstant.DISABLE, id);
	}

	@Override
	public Page<User> getListSearchCustomer(String keyword, Pageable pageable) {
		return userRepo.findAllSearchCustomer(ActiveConstant.ENABLE, keyword, pageable);
	}

	@Override
	public User getByUsernameOrEmail(String username) {
		return userRepo.findByUsernameOrEmail(username);
	}

	@Override
	public void setAccount(Account account) {
		Authentication auth = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Override
	public User createFromSocial(OAuth2User socialUser) {
		User user = new User(socialUser);
		userRepo.save(user);
		return this.getByUsernameOrEmail(user.getEmail());
	}

	@Override
	public void editProfile(User user) {
		if (ObjectUtils.isNotEmpty(user) && StringUtils.isEmpty(user.getPassword())) {
			userRepo.updateNonPass(user.getEmail(), user.getFullname(), user.getAvatarUrl(), user.getUsername());
		} else {
			String hashPassword = HashPasswordConstant.ENCODER.encode(user.getPassword());
			user.setPassword(hashPassword);
			userRepo.update(user.getEmail(), hashPassword, user.getFullname(), user.getAvatarUrl(), user.getUsername());
		}
	}
}
