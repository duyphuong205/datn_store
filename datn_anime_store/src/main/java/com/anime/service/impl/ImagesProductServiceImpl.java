package com.anime.service.impl;

import com.anime.entity.ImagesProduct;
import com.anime.repo.ImagesProductRepo;
import com.anime.service.ImagesProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class ImagesProductServiceImpl implements ImagesProductService {
    private final ImagesProductRepo imagesProductRepo;

    @Override
    public List<ImagesProduct> getAll() {
        return imagesProductRepo.findAll();
    }

    @Override
    public ImagesProduct findById(Long id) {
        Optional<ImagesProduct> imagesProduct = imagesProductRepo.findById(id);
        return imagesProduct.isPresent() ? imagesProduct.get() : null;
    }

    @Override
    public ImagesProduct create(ImagesProduct entity) {
        return imagesProductRepo.save(entity);
    }

    @Override
    public ImagesProduct update(ImagesProduct entity) {
        return imagesProductRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        imagesProductRepo.deleteById(id);
    }

	@Override
	public List<ImagesProduct> getByProductId(Long id) {
		return imagesProductRepo.findByProductId(id);
	}
}
