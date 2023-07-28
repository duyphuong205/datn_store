package com.anime.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anime.constants.ActiveConstant;
import com.anime.entity.Review;
import com.anime.repo.ReviewRepo;
import com.anime.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepo reviewRepo;

	@Override
	public List<Review> getAll() {
		return reviewRepo.findAll();
	}

	@Override
	public Review findById(Long id) {
		Optional<Review> review = reviewRepo.findById(id);
		return review.isPresent() ? review.get() : null;
	}

	@Override
	public Review create(Review entity) {
		return reviewRepo.save(entity);
	}

	@Override
	public Review update(Review entity) {
		return reviewRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		reviewRepo.deleteById(id);
	}

	@Override
	public Page<Review> getByProductId(Long id, Pageable pageable) {
		return reviewRepo.findByProductIdAndIsActive(id, ActiveConstant.ENABLE, pageable);
	}

	@Override
	public List<Review> getReviewRefused(Boolean isActive) {
		return reviewRepo.findReviewRefused(isActive);
	}

	@Override
	public List<Review> getReviewAccepted(Boolean isActive) {
		return reviewRepo.findReviewAccepted(isActive);
	}
}
