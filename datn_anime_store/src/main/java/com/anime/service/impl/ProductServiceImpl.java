package com.anime.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anime.constants.ActiveConstant;
import com.anime.entity.Product;
import com.anime.repo.ProductRepo;
import com.anime.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class ProductServiceImpl implements ProductService {
	private final ProductRepo productRepo;

	@Override
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> product = productRepo.findById(id);
		return product.isPresent() ? product.get() : null;
	}

	@Override
	public Product create(Product entity) {
		return productRepo.save(entity);
	}

	@Override
	public Product update(Product entity) {
		return productRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		productRepo.deleteById(id);
	}

	public void updateQuantityAndSelled(Integer quantity, Integer selled, Long id) {
		productRepo.updateQuantityAndSelled(quantity, selled, id);
	}

	@Override
	public Page<Product> getByIsActive(Pageable pageable) {
		return productRepo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Product getBySlug(String slug) {
		return productRepo.findBySlug(slug);
	}

	@Override
	public Page<Product> getByCategoryName(String name, Pageable pageable) {
		return productRepo.findByCategoryName(name, pageable);
	}

	@Override
	public List<Product> getTop10NewsProd() {
		return productRepo.findTop10NewsProd(ActiveConstant.ENABLE);
	}

	@Override
	public List<Product> getTop12ViewsProd() {
		return productRepo.findTop12ViewsProd(ActiveConstant.ENABLE);
	}

	@Override
	public List<Product> getTop12SelledsProd() {
		return productRepo.findTop12SelledsProd(ActiveConstant.ENABLE);
	}

	@Override
	public List<Product> getTop12DiscountsProd() {
		return productRepo.findTop12DiscountsProd(ActiveConstant.ENABLE);
	}

	@Override
	public Page<Product> getByKeyword(String keyword, Pageable pageable) {
		return productRepo.findByKeyword(ActiveConstant.ENABLE, keyword, pageable);
	}

	@Override
	public Page<Product> getByPrice(Double min, Double max, Pageable pageable) {
		return productRepo.findByPrice(min, max, ActiveConstant.ENABLE, pageable);
	}

	@Override
	public Page<Product> getByDiscount(Pageable pageable) {
		return productRepo.findByDiscount(ActiveConstant.ENABLE, pageable);
	}
}
