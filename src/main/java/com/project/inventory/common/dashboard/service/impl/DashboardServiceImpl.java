package com.project.inventory.common.dashboard.service.impl;

import com.project.inventory.common.dashboard.model.ProductsWithTotalSold;
import com.project.inventory.common.dashboard.model.TotalRevenue;
import com.project.inventory.common.dashboard.model.Totals;
import com.project.inventory.common.dashboard.repository.DashboardRepository;
import com.project.inventory.common.dashboard.service.DashboardService;
import com.project.inventory.store.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;
    @Override
    public Map<String, Object> getTotals() {
        TotalRevenue totalRevenue = dashboardRepository.getTotalRevenueByYear(  );
        Totals getTotals = dashboardRepository.findAllTotals();
        Map<String, Object> totals = new HashMap<>();

        totals.put( "revenue", totalRevenue.getTotalRevenue() );
        totals.put( "revenueYear", totalRevenue.getYear() );
        totals.put( "sold", getTotals.getTotalSold() );
        totals.put( "customer", getTotals.getTotalCustomers() );
        totals.put( "product", getTotals.getTotalProducts() );
        return totals;
    }

    @Override
    public Page<ProductsWithTotalSold> getProductsAndCountTotalSold( String query, Pageable pageable ) {
        try{
            List<ProductsWithTotalSold> productRecordPages = new ArrayList<>();
            Page<ProductsWithTotalSold> products = dashboardRepository.findAllProductSAndCountTotalSold( query, pageable );
            for( ProductsWithTotalSold product : products.getContent()){
                productRecordPages.add( product );
            }
            return new PageImpl<>( productRecordPages, pageable, products.getTotalElements() );
        }catch( Exception e ){
            throw e;
        }
    }

    @Override
    public List<TotalRevenue> getTotalRevenues( int year ) {
        return dashboardRepository.getRevenue( year );
    }
}
