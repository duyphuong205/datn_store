package com.anime.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.Category;
import com.anime.service.base.BaseService;

public interface CategoryService extends BaseService<Category> {
	List<Category> getByIsActice();

	Page<Category> getByIsActice(Pageable pageable);

	void deleteLogical(Long id) throws SQLException;
	
	Page<Category> getByKeyword(String keyword, Pageable pageable);
}
