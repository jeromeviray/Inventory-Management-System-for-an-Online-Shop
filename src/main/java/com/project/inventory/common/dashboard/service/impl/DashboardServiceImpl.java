package com.project.inventory.common.dashboard.service.impl;

import com.project.inventory.common.dashboard.model.ProductsWithTotalSold;
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
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;
    @Override
    public Totals getTotals() {
        return dashboardRepository.findAllTotals();
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
}
