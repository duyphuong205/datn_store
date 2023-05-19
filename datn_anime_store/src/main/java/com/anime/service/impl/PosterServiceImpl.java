package com.anime.service.impl;

import com.anime.entity.Poster;
import com.anime.repo.PosterRepo;
import com.anime.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class PosterServiceImpl implements PosterService {
    private final PosterRepo posterRepo;

    @Override
    public List<Poster> getAll() {
        return posterRepo.findAll();
    }

    @Override
    public Poster findById(Long id) {
        Optional<Poster> poster = posterRepo.findById(id);
        return poster.isPresent() ? poster.get() : null;
    }

    @Override
    public Poster create(Poster entity) {
        return posterRepo.save(entity);
    }

    @Override
    public Poster update(Poster entity) {
        return posterRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        posterRepo.deleteById(id);
    }
}
