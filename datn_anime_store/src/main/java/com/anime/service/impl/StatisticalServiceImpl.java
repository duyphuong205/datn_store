package com.anime.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anime.repo.StatisticalRepo;
import com.anime.service.StatisticalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {
	private final StatisticalRepo statisticalRepo;

	@Override
	public long getTotalQuantityProd() {
		return statisticalRepo.getTotalQuantityProd();
	}

	@Override
	public long getTotalUser() {
		return statisticalRepo.getTotalUser();
	}

	@Override
	public double getTotalPrice() {
		return statisticalRepo.getTotalPrice();
	}

	@Override
	public String[][] getTotalPriceLast6Months(String year) {
		String[][] result = new String[2][12];
		for (int i = 0; i <= 11; i++) {
			String month = String.valueOf(i + 1);

			result[0][i] = month + "-" + year;
			result[1][i] = statisticalRepo.getTotalPricePerMonth(month, year);
		}
		return result;
	}

	@Override
	public List<String> getYears() {
		return statisticalRepo.getYears();
	}
}
