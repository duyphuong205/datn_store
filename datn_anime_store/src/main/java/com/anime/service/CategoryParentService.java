package com.anime.service;

import com.anime.entity.CategoryParent;
import com.anime.service.base.BaseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryParentService extends BaseService<CategoryParent> {
  Page<CategoryParent> getByIsActive(Pageable pageable);
}
