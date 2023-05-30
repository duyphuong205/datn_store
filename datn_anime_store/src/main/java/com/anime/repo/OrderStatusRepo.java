package com.anime.repo;

import com.anime.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatus, Long> {
	OrderStatus findByStatus(Integer status);
}
