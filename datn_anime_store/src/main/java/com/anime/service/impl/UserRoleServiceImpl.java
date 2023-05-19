package com.anime.service.impl;

import com.anime.entity.UserRole;
import com.anime.repo.UserRoleRepo;
import com.anime.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepo userRoleRepo;

    @Override
    public List<UserRole> getAll() {
        return userRoleRepo.findAll();
    }

    @Override
    public UserRole findById(Long id) {
        Optional<UserRole> userRole = userRoleRepo.findById(id);
        return userRole.isPresent() ? userRole.get() : null;
    }

    @Override
    public UserRole create(UserRole entity) {
        return userRoleRepo.save(entity);
    }

    @Override
    public UserRole update(UserRole entity) {
        return userRoleRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        userRoleRepo.deleteById(id);
    }
}
