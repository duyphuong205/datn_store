package com.anime.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.Product;
import com.anime.service.base.BaseService;

public interface ProductService extends BaseService<Product> {
	Product getBySlug(String slug);

	Page<Product> getByIsActive(Pageable pageable);

	void updateQuantityAndSelled(Integer quantity, Integer selled, Long id);

	Page<Product> getByCategoryName(String name, Pageable pageable);

	List<Product> getTop10NewsProd();

	List<Product> getTop12ViewsProd();
	
	List<Product> getTop12SelledsProd();

	List<Product> getTop12DiscountsProd();
	
	Page<Product> getByKeyword(String keyword, Pageable pageable);
	
	Page<Product> getByPrice(Double min, Double max, Pageable pageable);
	
	Page<Product> getByDiscount(Pageable pageable);
}
