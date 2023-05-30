package com.anime.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.Review;
import com.anime.service.base.BaseService;

public interface ReviewService extends BaseService<Review> {
	Page<Review> getByProductId(Long id, Pageable pageable);
}
