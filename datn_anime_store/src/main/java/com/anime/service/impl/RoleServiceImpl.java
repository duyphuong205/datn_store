package com.anime.service.impl;

import com.anime.entity.Role;
import com.anime.repo.RoleRepo;
import com.anime.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class RoleServiceImpl implements RoleService {
	private final RoleRepo roleRepo;

	@Override
	public List<Role> getAll() {
		return roleRepo.findAll();
	}

	@Override
	public Role findById(Long id) {
		Optional<Role> role = roleRepo.findById(id);
		return role.isPresent() ? role.get() : null;
	}

	@Override
	public Role create(Role entity) {
		return roleRepo.save(entity);
	}

	@Override
	public Role update(Role entity) {
		return roleRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		roleRepo.deleteById(id);
	}

	@Override
	public Role getByName(String name) {
		return roleRepo.findByName(name);
	}
}
