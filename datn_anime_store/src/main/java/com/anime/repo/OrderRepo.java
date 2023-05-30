package com.anime.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.orderStatus.status = ?1 AND o.user.username = ?2 AND o.isActive = ?3")
	Page<Order> findOrdersByOrderStatusAndUsername(Integer status, String username, Boolean isActive,
			Pageable pageable);
}
