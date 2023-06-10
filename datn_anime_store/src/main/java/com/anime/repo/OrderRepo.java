package com.anime.repo;

import java.util.List;

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

	Page<Order> findByIsActive(Boolean isActive, Pageable pageable);

	@Query(value = "select users.username from orders join users on orders.user_id = users.id", nativeQuery = true)
	List<String> findUsernameOrdered();

	@Query("SELECT o FROM Order o WHERE o.user.username = ?1 AND o.isActive = ?2")
	Page<Order> findOrdersByUsername(String username, Boolean isActive, Pageable pageable);
}
