package com.project.inventory.store.product.brand.repository;

import com.project.inventory.store.product.brand.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
