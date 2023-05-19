package com.anime.service.impl;

import com.anime.entity.User;
import com.anime.repo.UserRepo;
import com.anime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

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
}
