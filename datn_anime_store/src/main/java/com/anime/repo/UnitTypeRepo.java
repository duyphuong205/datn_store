package com.anime.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.entity.UnitType;

@Repository
public interface UnitTypeRepo extends JpaRepository<UnitType, Long> {
	List<UnitType> findByIsActive(Boolean isActive);

	Page<UnitType> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE UnitType c SET c.isActive = ?1 WHERE c.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

	@Query("SELECT c FROM UnitType c WHERE c.isActive = ?1 AND c.name LIKE %?2%")
	Page<UnitType> findByKeyword(Boolean isActive, String keyword, Pageable pageable);
}
