package com.project.inventory.store.inventory.stock.repository;

import com.project.inventory.store.inventory.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}
