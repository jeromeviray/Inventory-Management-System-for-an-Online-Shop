package com.project.inventory.store.incomingSupply.repository;

import com.project.inventory.store.incomingSupply.model.IncomingSupply;
import com.project.inventory.store.incomingSupply.model.IncomingSupplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomingSupplyRepository extends JpaRepository<IncomingSupply, Integer> {

    List<IncomingSupply> findAllByIncomingSupplyStatus( IncomingSupplyStatus incomingSupplyStatus );
}
