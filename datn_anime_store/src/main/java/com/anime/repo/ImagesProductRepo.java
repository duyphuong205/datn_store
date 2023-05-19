package com.anime.repo;

import com.anime.entity.ImagesProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesProductRepo extends JpaRepository<ImagesProduct, Long> {

}
