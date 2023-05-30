package com.anime.repo;

import com.anime.entity.PaymentMethod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Long> {
	List<PaymentMethod> findByIsActive(Boolean isActice);
}
