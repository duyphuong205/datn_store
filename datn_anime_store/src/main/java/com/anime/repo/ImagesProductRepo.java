package com.anime.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anime.entity.ImagesProduct;

@Repository
public interface ImagesProductRepo extends JpaRepository<ImagesProduct, Long> {
	List<ImagesProduct> findByProductId(Long id);
}
