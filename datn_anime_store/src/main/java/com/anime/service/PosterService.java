package com.anime.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.Poster;
import com.anime.service.base.BaseService;

public interface PosterService extends BaseService<Poster> {
	List<Poster> getByIsActive();
	
	Page<Poster> getByIsActive(Pageable pageable);
	
	void deleteLogical(Long id);
}
