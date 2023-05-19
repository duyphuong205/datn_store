package com.anime.repo;

import com.anime.entity.CategoryParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryParentRepo extends JpaRepository<CategoryParent, Long> {

}
