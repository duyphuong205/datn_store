package com.anime.repo;

import com.anime.entity.Product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	Product findBySlug(String slug);

	Page<Product> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Product p SET p.quantity = ?1, p.selled = ?2 WHERE p.id = ?3")
	void updateQuantityAndSelled(Integer quantity, Integer selled, Long id);

	Page<Product> findByCategoryName(String name, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 AND CONCAT(p.name, p.slug, p.category.name) LIKE %?2%")
	Page<Product> findByKeyword(Boolean isActive, String keyword, Pageable pageable);

	@Query(value = "SELECT top(10) * FROM Products where is_active = ? ORDER BY create_date DESC", nativeQuery = true)
	List<Product> findTop10NewsProd(Boolean isActive);

	@Query(value = "SELECT top(12) * FROM Products where is_active = ? ORDER BY [view] DESC", nativeQuery = true)
	List<Product> findTop12ViewsProd(Boolean isActive);
	
	@Query(value = "SELECT top(12) * FROM Products where is_active = ? ORDER BY selled DESC", nativeQuery = true)
	List<Product> findTop12SelledsProd(Boolean isActive);
	
	@Query(value = "SELECT top(12) * FROM Products where is_active = ? ORDER BY discount DESC", nativeQuery = true)
	List<Product> findTop12DiscountsProd(Boolean isActive);
	
	@Query("SELECT p FROM Product p where p.price BETWEEN ?1 AND ?2 AND isActive = ?3")
	Page<Product> findByPrice(Double min, Double max, Boolean isAcitve, Pageable pageable) ;
	
	@Query(value = "SELECT * FROM Products where is_active = ? AND discount <> 0 ORDER BY discount DESC", nativeQuery = true)
	Page<Product> findByDiscount(Boolean isAcitve, Pageable pageable) ;
}
