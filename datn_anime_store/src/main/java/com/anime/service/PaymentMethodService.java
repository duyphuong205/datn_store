package com.anime.service;

import java.util.List;

import com.anime.entity.PaymentMethod;
import com.anime.service.base.BaseService;

public interface PaymentMethodService extends BaseService<PaymentMethod> {
	List<PaymentMethod> getByIsActive();
}
