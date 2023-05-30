package com.anime.service.impl;

import com.anime.constants.ActiveConstant;
import com.anime.entity.Order;
import com.anime.repo.OrderRepo;
import com.anime.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    @Override
    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.isPresent() ? order.get() : null;
    }

    @Override
    public Order create(Order entity) {
        return orderRepo.save(entity);
    }

    @Override
    public Order update(Order entity) {
        return orderRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

	@Override
	public Page<Order> findOrdersByOrderStatusAndUsername(Integer status, String username, Pageable pageable) {
		return orderRepo.findOrdersByOrderStatusAndUsername(status, username, ActiveConstant.ENABLE, pageable);
	}
}
