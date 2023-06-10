package com.anime.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.entity.CategoryParent;

@Repository
public interface CategoryParentRepo extends JpaRepository<CategoryParent, Long> {
	List<CategoryParent> findByIsActive(Boolean isActive);

	Page<CategoryParent> findByIsActive(Boolean isActive, Pageable pageable);

	@Query("SELECT c FROM CategoryParent c WHERE c.isActive = ?1 AND CONCAT(c.name) LIKE %?2%")
	Page<CategoryParent> findByKeyword(Boolean isActive, String keyword, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE CategoryParent c SET c.isActive = ?1 WHERE c.id = ?2")
	void deleteLogical(Boolean isActive, Long id);
}
