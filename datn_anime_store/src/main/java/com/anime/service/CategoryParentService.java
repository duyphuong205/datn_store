package com.anime.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.CategoryParent;
import com.anime.service.base.BaseService;

public interface CategoryParentService extends BaseService<CategoryParent> {
	List<CategoryParent> getByIsActice();
	
	void deleteLogical(Long id) throws SQLException;
	
	Page<CategoryParent> getByIsActice(Pageable pageable);
}
