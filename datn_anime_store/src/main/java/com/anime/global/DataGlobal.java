package com.anime.global;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anime.entity.CategoryParent;
import com.anime.entity.Poster;
import com.anime.service.CategoryParentService;
import com.anime.service.PosterService;

import lombok.RequiredArgsConstructor;

@Service("DataGlobal")
@RequiredArgsConstructor
public class DataGlobal {

	private final PosterService posterService;

	private final CategoryParentService categoryParentService;

	public List<CategoryParent> getCategoryParents() {
		return !categoryParentService.getAll().isEmpty() ? categoryParentService.getAll() : null;
	}

	public List<Poster> getPosters() {
		return !posterService.getAll().isEmpty() ? posterService.getAll() : null;
	}

}
