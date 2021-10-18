package com.project.inventory.common.dashboard.service;

import com.project.inventory.common.dashboard.model.ProductsWithTotalSold;
import com.project.inventory.common.dashboard.model.TotalRevenue;
import com.project.inventory.common.dashboard.model.Totals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    Map<String, Object> getTotals( );

    Page<ProductsWithTotalSold> getProductsAndCountTotalSold( String query, Pageable pageable );

    List<TotalRevenue> getTotalRevenues(int year);
}
