package com.anime.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anime.entity.Poster;

@Repository
public interface PosterRepo extends JpaRepository<Poster, Long> {
	List<Poster> findByIsActive(Boolean isActive);
}
