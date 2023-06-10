package com.anime.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anime.constants.ActiveConstant;
import com.anime.entity.Category;
import com.anime.repo.CategoryRepo;
import com.anime.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
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

	@Override
	public List<Category> getByIsActice() {
		return categoryRepo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Category> getByIsActice(Pageable pageable) {
		return categoryRepo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public void deleteLogical(Long id) throws SQLException {
		categoryRepo.deleteLogical(ActiveConstant.DISABLE, id);
	}

	@Override
	public Page<Category> getByKeyword(String keyword, Pageable pageable) {
		return categoryRepo.findByKeyword(ActiveConstant.ENABLE, keyword, pageable);
	}
}
