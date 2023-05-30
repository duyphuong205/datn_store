package com.anime.service;

import java.util.List;

public interface StatisticalService {
	long getTotalQuantityProd();

    long getTotalUser();

    double getTotalPrice();

    String[][] getTotalPriceLast6Months(String year);

    List<String> getYears();
}
