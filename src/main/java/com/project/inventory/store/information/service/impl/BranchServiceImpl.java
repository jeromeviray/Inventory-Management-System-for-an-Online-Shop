package com.project.inventory.store.information.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service( value = "storeInformationServiceImpl" )
public class BranchServiceImpl implements BranchService {
    Logger logger = LoggerFactory.getLogger( BranchServiceImpl.class );

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Branch> saveBranch( Branch branch ) {
        branchRepository.save( branch );
        return branchRepository.findAll();
    }

    @Override
    public void updateBranch( int id, Branch branch ) {
        Branch savedBranch = getBranchById( id );
        savedBranch.setBranch( branch.getBranch() );
        saveBranch( savedBranch );
    }

    @Override
    public Branch getBranchById( int id ) {
        return branchRepository.findById( id )
                .orElseThrow(() -> new NotFoundException("Branch not Found."));
    }

    @Override
    public Branch getBranchByBranch( String branch ) {
        return branchRepository.findByBranch( branch )
                .orElseThrow();
    }

    @Override
    public BranchDto getProductsByBranch( String branch ) {
        return convertEntityToDto( branchRepository.findProductsByBranch( branch ) );
    }

    @Override
    public List<String> getBranch() {
        List<String> branches = new ArrayList<>();

        for ( Branch store : branchRepository.findAllIsDeleted() ) {
            branches.add( store.getBranch() );
        }
        return branches;
    }

    @Override
    public List<GetBranchWithTotalProduct> getBranchWithTotalProduct() {
        return branchRepository.countProductByBranchId();
    }

    @Override
    public void deleteBranch( int id ) {
        Branch branch = getBranchById( id );
        branch.setDeletedAt( new Date() );
        branch.setDeleted( true );
        saveBranch( branch );
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
