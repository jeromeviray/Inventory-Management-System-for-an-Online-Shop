package com.project.inventory.store.information.service;

import com.project.inventory.store.information.model.StoreInformation;

public interface StoreInformationService {
    void saveStoreInformation(StoreInformation storeInformation);
    void updateStoreInformation(int id, StoreInformation storeInformation);
    StoreInformation getStoreInformation(int id);
    StoreInformation getStoreInformationByLocation(String storeLocation);
}
