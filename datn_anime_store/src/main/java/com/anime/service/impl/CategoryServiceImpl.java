package com.anime.service.impl;

import com.anime.entity.Category;
import com.anime.repo.CategoryRepo;
import com.anime.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        return category.isPresent() ? category.get() : null;
    }

    @Override
    public Category create(Category entity) {
        return categoryRepo.save(entity);
    }

    @Override
    public Category update(Category entity) {
        return categoryRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }
}
