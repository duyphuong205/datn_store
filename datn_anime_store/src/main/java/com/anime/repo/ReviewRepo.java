package com.anime.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anime.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
	
	Page<Review> findByProductIdAndIsActive(Long id, Boolean isActive, Pageable pageable);
}
