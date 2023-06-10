package com.anime.service;

import java.util.List;

import com.anime.entity.ImagesProduct;
import com.anime.service.base.BaseService;

public interface ImagesProductService extends BaseService<ImagesProduct> {
	List<ImagesProduct> getByProductId(Long id);

	void saveImages(List<ImagesProduct> images);
}
