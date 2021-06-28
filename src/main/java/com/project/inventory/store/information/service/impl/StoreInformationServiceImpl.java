package com.project.inventory.store.information.service.impl;

import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.information.repository.StoreInformationRepository;
import com.project.inventory.store.information.service.StoreInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "storeInformationServiceImpl")
public class StoreInformationServiceImpl implements StoreInformationService {
    Logger logger = LoggerFactory.getLogger(StoreInformationServiceImpl.class);

    @Autowired
    private StoreInformationRepository storeInformationRepository;


    @Override
    public void saveStoreInformation(StoreInformation storeInformation) {
        storeInformationRepository.save(storeInformation);
    }

    @Override
    public void updateStoreInformation(int id, StoreInformation storeInformation) {

    }

    @Override
    public StoreInformation getStoreInformation(int id) {
        return null;
    }

    @Override
    public StoreInformation getStoreInformationByLocation(String storeLocation) {
        return storeInformationRepository.findByLocation(storeLocation)
                .orElseThrow();
    }
}
