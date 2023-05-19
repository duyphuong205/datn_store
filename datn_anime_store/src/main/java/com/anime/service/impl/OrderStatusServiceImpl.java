package com.anime.service.impl;

import com.anime.entity.OrderStatus;
import com.anime.repo.OrderStatusRepo;
import com.anime.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepo orderStatusRepo;

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepo.findAll();
    }

    @Override
    public OrderStatus findById(Long id) {
        Optional<OrderStatus> orderStatus = orderStatusRepo.findById(id);
        return orderStatus.isPresent() ? orderStatus.get() : null;
    }

    @Override
    public OrderStatus create(OrderStatus entity) {
        return orderStatusRepo.save(entity);
    }

    @Override
    public OrderStatus update(OrderStatus entity) {
        return orderStatusRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        orderStatusRepo.deleteById(id);
    }
}
