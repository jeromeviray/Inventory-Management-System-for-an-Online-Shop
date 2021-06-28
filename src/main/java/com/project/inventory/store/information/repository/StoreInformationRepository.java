package com.project.inventory.store.information.repository;

import com.project.inventory.store.information.model.StoreInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreInformationRepository extends JpaRepository<StoreInformation, Integer> {

    Optional<StoreInformation> findByLocation(String location);
}
