package com.anime.service.impl;

import com.anime.constants.ActiveConstant;
import com.anime.entity.UnitType;
import com.anime.repo.UnitTypeRepo;
import com.anime.service.UnitTypeService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class UnitTypeServiceImpl implements UnitTypeService {
	private final UnitTypeRepo unitTypeRepo;

	@Override
	public List<UnitType> getAll() {
		return unitTypeRepo.findAll();
	}

	@Override
	public UnitType findById(Long id) {
		Optional<UnitType> unitType = unitTypeRepo.findById(id);
		return unitType.isPresent() ? unitType.get() : null;
	}

	@Override
	public UnitType create(UnitType entity) {
		return unitTypeRepo.save(entity);
	}

	@Override
	public UnitType update(UnitType entity) {
		return unitTypeRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		unitTypeRepo.deleteById(id);
	}

	@Override
	public List<UnitType> getByIsActive() {
		return unitTypeRepo.findByIsActive(ActiveConstant.ENABLE);
	}

	@Override
	public Page<UnitType> getByIsActive(Pageable pageable) {
		return unitTypeRepo.findByIsActive(ActiveConstant.ENABLE, pageable);
	}

	@Override
	public void deleteLogical(Long id) throws SQLException {
		unitTypeRepo.deleteLogical(ActiveConstant.DISABLE, id);
	}

	@Override
	public Page<UnitType> getByKeyword(String keyword, Pageable pageable) {
		return unitTypeRepo.findByKeyword(ActiveConstant.ENABLE, keyword, pageable);
	}
}
