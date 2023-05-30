package com.anime.service;

import java.util.List;

import com.anime.entity.Poster;
import com.anime.service.base.BaseService;

public interface PosterService extends BaseService<Poster> {
	List<Poster> getByIsActive();
}
