package com.anime.global;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anime.entity.Category;
import com.anime.entity.CategoryParent;
import com.anime.entity.Poster;
import com.anime.entity.UnitType;
import com.anime.service.CategoryParentService;
import com.anime.service.CategoryService;
import com.anime.service.PosterService;
import com.anime.service.UnitTypeService;

import lombok.RequiredArgsConstructor;

@Service("DataGlobal")
@RequiredArgsConstructor
public class DataGlobal {

	private final PosterService posterService;

	private final CategoryParentService categoryParentService;

	private final UnitTypeService unitTypeService;

	private final CategoryService categoryService;

	public List<CategoryParent> getCategoryParents() {
		return !categoryParentService.getByIsActice().isEmpty() ? categoryParentService.getByIsActice() : null;
	}

	public List<Poster> getPosters() {
		return !posterService.getByIsActive().isEmpty() ? posterService.getByIsActive() : null;
	}

	public List<Category> getCategories() {
		return !categoryService.getByIsActice().isEmpty() ? categoryService.getByIsActice() : null;
	}

	public List<UnitType> getUnitTypes() {
		return !unitTypeService.getByIsActive().isEmpty() ? unitTypeService.getByIsActive() : null;
	}

}
