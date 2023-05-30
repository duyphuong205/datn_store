package com.anime.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anime.entity.Order;

@Repository
public interface StatisticalRepo extends JpaRepository<Order, Long> {
	@Query(value = "{CALL sp_getTotalQuantityProd}", nativeQuery = true)
	long getTotalQuantityProd();

	@Query(value = "{CALL sp_getTotalUser}", nativeQuery = true)
	long getTotalUser();

	@Query(value = "{CALL sp_getTotalPrice}", nativeQuery = true)
	double getTotalPrice();

	@Query(value = "{CALL sp_getTotalPricePerMonth(:month, :year)}", nativeQuery = true)
	String getTotalPricePerMonth(@Param("month") String month, @Param("year") String year);

	@Query(value = "SELECT DISTINCT YEAR(o.create_date) FROM orders o ORDER BY YEAR(o.create_date) ASC", nativeQuery = true)
	List<String> getYears();
}
