package com.project.inventory.store.information.service;

import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.information.model.StoreInformationDto;

import java.util.List;

public interface StoreInformationService {
    List<StoreInformation> saveStoreInformation( StoreInformation storeInformation);
    void updateStoreInformation(int id, StoreInformation storeInformation);
    StoreInformation getStoreInformation(int id);
    StoreInformation getStoreInformationByBranch( String storeLocation);
    StoreInformationDto getProductsByBranch( String branch);
    List<String> getStores();
}
