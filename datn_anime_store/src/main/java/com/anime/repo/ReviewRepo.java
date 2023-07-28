package com.anime.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

	Page<Review> findByProductIdAndIsActive(Long id, Boolean isActive, Pageable pageable);

	@Query("SELECT r FROM Review r WHERE r.isActive = ?1 ORDER BY r.createDate ASC")
	List<Review> findReviewRefused(Boolean isActive);

	@Query("SELECT r FROM Review r WHERE r.isActive = ?1 ORDER BY r.createDate ASC")
	List<Review> findReviewAccepted(Boolean isActive);
}
