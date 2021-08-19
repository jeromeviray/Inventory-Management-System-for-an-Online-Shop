package com.project.inventory.store.information.repository;

import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreInformationRepository extends JpaRepository<StoreInformation, Integer> {

    Optional<StoreInformation> findByBranch(String location);

    StoreInformation findProductsByBranch(String location);

}
