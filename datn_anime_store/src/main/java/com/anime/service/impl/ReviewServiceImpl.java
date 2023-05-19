package com.anime.service.impl;

import com.anime.entity.Review;
import com.anime.repo.ReviewRepo;
import com.anime.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
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
}
