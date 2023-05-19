package com.anime.service.impl;

import com.anime.entity.OrderDetail;
import com.anime.repo.OrderDetailRepo;
import com.anime.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
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
}
