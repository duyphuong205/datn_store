package com.anime.repo;

import com.anime.entity.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitTypeRepo extends JpaRepository<UnitType, Long> {

}
