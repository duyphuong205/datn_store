package com.anime.service.impl;

import com.anime.entity.PaymentMethod;
import com.anime.repo.PaymentMethodRepo;
import com.anime.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Throwable.class})
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepo paymentMethodRepo;

    @Override
    public List<PaymentMethod> getAll() {
        return paymentMethodRepo.findAll();
    }

    @Override
    public PaymentMethod findById(Long id) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepo.findById(id);
        return paymentMethod.isPresent() ? paymentMethod.get() : null;
    }

    @Override
    public PaymentMethod create(PaymentMethod entity) {
        return paymentMethodRepo.save(entity);
    }

    @Override
    public PaymentMethod update(PaymentMethod entity) {
        return paymentMethodRepo.save(entity);
    }

    @Override
    public void delete(Long id) {
        paymentMethodRepo.deleteById(id);
    }
}
