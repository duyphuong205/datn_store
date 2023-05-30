package com.anime.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.entity.Order;
import com.anime.service.base.BaseService;

public interface OrderService extends BaseService<Order> {
	Page<Order> findOrdersByOrderStatusAndUsername(Integer status, String username, Pageable pageable);
}
