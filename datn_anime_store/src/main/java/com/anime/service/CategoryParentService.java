package com.anime.service;

import java.util.List;

import com.anime.entity.CategoryParent;
import com.anime.service.base.BaseService;

public interface CategoryParentService extends BaseService<CategoryParent> {
	List<CategoryParent> getByIsActice();
}
