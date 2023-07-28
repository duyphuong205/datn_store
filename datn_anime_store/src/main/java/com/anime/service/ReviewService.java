package com.anime.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.Review;
import com.anime.service.base.BaseService;

public interface ReviewService extends BaseService<Review> {
	Page<Review> getByProductId(Long id, Pageable pageable);
	
	List<Review> getReviewRefused(Boolean isActive);
	
	List<Review> getReviewAccepted(Boolean isActive);
}
