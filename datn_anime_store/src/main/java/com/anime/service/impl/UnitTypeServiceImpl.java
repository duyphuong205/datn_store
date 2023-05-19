package com.anime.service.impl;

import com.anime.entity.UnitType;
import com.anime.repo.UnitTypeRepo;
import com.anime.service.UnitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
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
}
