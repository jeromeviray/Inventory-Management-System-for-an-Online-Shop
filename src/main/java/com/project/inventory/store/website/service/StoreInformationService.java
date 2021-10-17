package com.project.inventory.store.website.service;

import com.project.inventory.store.website.model.StoreInformation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StoreInformationService {
    void saveStoreInformation( String storeName,
                               String acronym,
                               String location,
                               Object description,
                               String contactNumber,
                               String email ) throws IOException;

    StoreInformation updateStoreInformation( int id,
                                             String storeName,
                                             String acronym,
                                             String location,
                                             Object description,
                                             String contactNumber,
                                             String email ) throws IOException;

    StoreInformation getStoreInformation();

    StoreInformation getStoreInformationById( int id );

    boolean checkIfExist();
}
