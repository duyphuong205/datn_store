package com.anime.service.base;

import java.util.List;

public interface BaseService<T> {
    List<T> getAll();

    T findById(Long id);

    T create(T entity);

    T update(T entity);

    void delete(Long id);
}
