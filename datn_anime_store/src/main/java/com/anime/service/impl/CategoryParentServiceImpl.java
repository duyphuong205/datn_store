package com.anime.service.impl;

import com.anime.constants.ActiveConstant;
import com.anime.entity.CategoryParent;
import com.anime.repo.CategoryParentRepo;
import com.anime.service.CategoryParentService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class CategoryParentServiceImpl implements CategoryParentService {
	private final CategoryParentRepo categoryParentRepo;

	@Override
	public List<CategoryParent> getAll() {
		return categoryParentRepo.findAll();
	}

	@Override
	public CategoryParent findById(Long id) {
		Optional<CategoryParent> categoryParent = categoryParentRepo.findById(id);
		return categoryParent.isPresent() ? categoryParent.get() : null;
	}

	@Override
	public CategoryParent create(CategoryParent entity) {
		return categoryParentRepo.save(entity);
	}

	@Override
	public CategoryParent update(CategoryParent entity) {
		return categoryParentRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		categoryParentRepo.deleteById(id);
	}

	@Override
	public List<CategoryParent> getByIsActice() {
		return categoryParentRepo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public void deleteLogical(Long id) throws SQLException {
		categoryParentRepo.deleteLogical(ActiveConstant.DISABLE, id);
	}

	@Override
	public Page<CategoryParent> getByIsActice(Pageable pageable) {
		return categoryParentRepo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Page<CategoryParent> getByKeyword(String keyword, Pageable pageable) {
		return categoryParentRepo.findByKeyword(ActiveConstant.ENABLE, keyword, pageable);
	}
}
