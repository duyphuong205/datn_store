package com.anime.service.impl;

import com.anime.entity.Product;
import com.anime.repo.ProductRepo;
import com.anime.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
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
}
