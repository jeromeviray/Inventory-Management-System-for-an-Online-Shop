package com.project.inventory.store.information.service.impl;

import com.project.inventory.store.information.model.Branch;
import com.project.inventory.store.information.model.BranchDto;
import com.project.inventory.store.information.model.GetBranchWithTotalProduct;
import com.project.inventory.store.information.repository.BranchRepository;
import com.project.inventory.store.information.service.BranchService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service( value = "storeInformationServiceImpl" )
public class BranchServiceImpl implements BranchService {
    Logger logger = LoggerFactory.getLogger( BranchServiceImpl.class );

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Branch> saveStoreInformation( Branch branch ) {
        branchRepository.save( branch );
        return branchRepository.findAll();
    }

    @Override
    public void updateStoreInformation( int id, Branch branch ) {

    }

    @Override
    public Branch getStoreInformation( int id ) {
        return null;
    }

    @Override
    public Branch getStoreInformationByBranch( String branch ) {
        return branchRepository.findByBranch( branch )
                .orElseThrow();
    }

    @Override
    public BranchDto getProductsByBranch( String branch ) {
        return convertEntityToDto( branchRepository.findProductsByBranch( branch ) );
    }

    @Override
    public List<String> getStores() {
        List<String> location = new ArrayList<>();

        for ( Branch store : branchRepository.findAll() ) {
            location.add( store.getBranch() );
        }
        return location;
    }

    @Override
    public List<GetBranchWithTotalProduct> getBranchWithTotalProduct() {
        return branchRepository.countProductByBranchId();
    }

    // converting entity to dto
    public BranchDto convertEntityToDto( Branch branch ) {
        return mapper.map( branch, BranchDto.class );
    }

    // converting dto to entity
    public Branch convertDtoToEntity( BranchDto branchDto ) {
        return mapper.map( branchDto, Branch.class );
    }
}
