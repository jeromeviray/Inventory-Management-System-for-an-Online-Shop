package com.project.inventory.store.incomingSupply.incomingSupplyItem.repository;

import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingSupplyItemRepository extends JpaRepository<IncomingSupplyItem, Integer> {
}
