package com.project.inventory.common.dashboard.service;

import com.project.inventory.common.dashboard.model.ProductsWithTotalSold;
import com.project.inventory.common.dashboard.model.Totals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface DashboardService {

    Map<String, Object> getTotals( int year);

    Page<ProductsWithTotalSold> getProductsAndCountTotalSold( String query, Pageable pageable );
}
