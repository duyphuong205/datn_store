package com.anime.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.UnitType;
import com.anime.service.base.BaseService;

public interface UnitTypeService extends BaseService<UnitType> {
	List<UnitType> getByIsActive();

	Page<UnitType> getByIsActive(Pageable pageable);
	
	void deleteLogical(Long id) throws SQLException;
	
	Page<UnitType> getByKeyword(String keyword, Pageable pageable);
}
