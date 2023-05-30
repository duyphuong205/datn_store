package com.anime.repo;

import com.anime.dto.CartDetailDto;
import com.anime.entity.OrderDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO order_details(order_id, product_id, price, quantity) "
			+ "VALUES(:#{#dto.orderId}, :#{#dto.productId}, :#{#dto.price} ,:#{#dto.quantity})", nativeQuery = true)
	void insert(@Param("dto") CartDetailDto cartDetailDto);
	
	@Query("SELECT od FROM OrderDetail od WHERE od.order.id = ?1")
	Page<OrderDetail> findByOrderId(Long id, Pageable pageable);
}
