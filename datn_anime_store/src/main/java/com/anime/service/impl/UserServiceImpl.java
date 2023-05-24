package com.anime.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anime.constants.ActiveConstant;
import com.anime.constants.HashPasswordConstant;
import com.anime.constants.RoleConstant;
import com.anime.entity.Role;
import com.anime.entity.User;
import com.anime.entity.UserRole;
import com.anime.repo.UserRepo;
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
}
