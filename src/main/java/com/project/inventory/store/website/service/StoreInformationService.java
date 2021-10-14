package com.project.inventory.store.website.service;

import com.project.inventory.store.website.model.StoreInformation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StoreInformationService {
    void saveStoreInformation( MultipartFile logoImage,
                               String storeName,
                               String domainName,
                               String location,
                               Object description  ) throws IOException;

    StoreInformation updateStoreInformation( int id,  MultipartFile logoImage,
                                             String storeName,
                                             String domainName,
                                             String location,
                                             Object description,
                                             String removeLogo ) throws IOException;

    StoreInformation getStoreInformation();

    StoreInformation getStoreInformationById( int id );

    boolean checkIfExist();
}
