package com.project.inventory.common.dashboard.controller;

import com.project.inventory.common.dashboard.model.ProductsWithTotalSold;
import com.project.inventory.common.dashboard.service.DashboardService;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @PreAuthorize( "hasRole('SUPER_ADMIN')" )
    @RequestMapping(value = "/summaries", method = RequestMethod.GET)
    public ResponseEntity<?> getTotals(){
////        System.out.println(year);
//
//        int currentYear;
//        currentYear = Objects.requireNonNullElseGet( year, () -> Calendar.getInstance().get( Calendar.YEAR ) );
        return new ResponseEntity<>(dashboardService.getTotals(), HttpStatus.OK );
    }
    @PreAuthorize( "hasRole('SUPER_ADMIN')" )
    @RequestMapping(value = "/summaries/products/sold", method = RequestMethod.GET)
    public ResponseEntity<?> getProductsAndCountTotalSold(@RequestParam( value = "query", defaultValue = "", required = false ) String query,
                                                          @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                                          @RequestParam( value = "limit", defaultValue = "10" ) Integer limit){
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit, Sort.by(Sort.Direction.ASC, "totalSold") );
        Page<ProductsWithTotalSold> products = dashboardService.getProductsAndCountTotalSold( query, pageable);

        response.put( "data", products.getContent() );
        response.put( "currentPage", products.getNumber() );
        response.put( "totalItems", products.getTotalElements() );
        response.put( "totalPages", products.getTotalPages() );
        return new ResponseEntity<>(response, HttpStatus.OK );
    }
    @PreAuthorize( "hasRole('SUPER_ADMIN')" )
    @RequestMapping(value = "/revenue", method = RequestMethod.GET)
    public ResponseEntity<?> getRevenues(@RequestParam( value = "year", required = false ) Integer year){
//        System.out.println(year);

        int currentYear;
        currentYear = Objects.requireNonNullElseGet( year, () -> Calendar.getInstance().get( Calendar.YEAR ) );
        return new ResponseEntity<>(dashboardService.getTotalRevenues(currentYear), HttpStatus.OK );
    }

}
