package com.anime.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anime.dto.CartDetailDto;
import com.anime.entity.OrderDetail;
import com.anime.repo.OrderDetailRepo;
import com.anime.service.OrderDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Throwable.class })
public class OrderDetailServiceImpl implements OrderDetailService {
	private final OrderDetailRepo orderDetailRepo;

	@Override
	public List<OrderDetail> getAll() {
		return orderDetailRepo.findAll();
	}

	@Override
	public OrderDetail findById(Long id) {
		Optional<OrderDetail> orderDetail = orderDetailRepo.findById(id);
		return orderDetail.isPresent() ? orderDetail.get() : null;
	}

	@Override
	public OrderDetail create(OrderDetail entity) {
		return orderDetailRepo.save(entity);
	}

	@Override
	public OrderDetail update(OrderDetail entity) {
		return orderDetailRepo.save(entity);
	}

	@Override
	public void delete(Long id) {
		orderDetailRepo.deleteById(id);
	}

	@Override
	public void insert(CartDetailDto cartDetailDto) {
		orderDetailRepo.insert(cartDetailDto);
	}

	@Override
	public Page<OrderDetail> getByOrderId(Long id, Pageable pageable) {
		return orderDetailRepo.findByOrderId(id, pageable);
	}
}
