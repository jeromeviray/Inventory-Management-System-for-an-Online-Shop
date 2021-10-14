package com.project.inventory.store.website.repository;

import com.project.inventory.store.website.model.StoreInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreInformationRepository extends JpaRepository<StoreInformation, Integer> {
}
