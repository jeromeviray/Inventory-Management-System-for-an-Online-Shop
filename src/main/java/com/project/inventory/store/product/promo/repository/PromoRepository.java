package com.project.inventory.store.product.promo.repository;

import com.project.inventory.store.product.promo.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoRepository extends JpaRepository<Promo, Integer> {
}
