package com.project.inventory.store.information.branch.service;

import com.project.inventory.store.information.branch.model.Branch;
import com.project.inventory.store.information.branch.model.BranchDto;
import com.project.inventory.store.information.branch.model.GetBranchWithTotalProduct;

import java.util.List;

public interface BranchService {
    List<Branch> saveBranch( Branch branch );

    void updateBranch( int id, Branch branch );

    Branch getBranchById( int id );

    Branch getBranchByBranch( String storeLocation );

    BranchDto getProductsByBranch( String branch );

    List<String> getBranch();

    List<GetBranchWithTotalProduct> getBranchWithTotalProduct();

    void deleteBranch(int id);
}
