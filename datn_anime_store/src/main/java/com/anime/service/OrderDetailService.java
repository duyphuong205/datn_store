package com.anime.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anime.dto.CartDetailDto;
import com.anime.entity.OrderDetail;
import com.anime.service.base.BaseService;

public interface OrderDetailService extends BaseService<OrderDetail> {
	void insert(CartDetailDto cartDetailDto);

	Page<OrderDetail> getByOrderId(Long id, Pageable pageable);
}
