package com.project.inventory.store.inventory.stock.controller;

import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/v1/api/inventory/stock" )
public class StockController {
    @Autowired
    private StockService stockService;


}
