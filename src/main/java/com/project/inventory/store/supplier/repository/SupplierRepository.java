package com.project.inventory.store.supplier.repository;

import com.project.inventory.store.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
