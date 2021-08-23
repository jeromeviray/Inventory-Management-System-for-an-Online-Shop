package com.project.inventory.store.information.service;

import com.project.inventory.store.information.model.Branch;
import com.project.inventory.store.information.model.BranchDto;
import com.project.inventory.store.information.model.GetBranchWithTotalProduct;

import java.util.List;

public interface BranchService {
    List<Branch> saveStoreInformation( Branch branch );

    void updateStoreInformation( int id, Branch branch );

    Branch getStoreInformation( int id );

    Branch getStoreInformationByBranch( String storeLocation );

    BranchDto getProductsByBranch( String branch );

    List<String> getStores();

    List<GetBranchWithTotalProduct> getBranchWithTotalProduct();
}
