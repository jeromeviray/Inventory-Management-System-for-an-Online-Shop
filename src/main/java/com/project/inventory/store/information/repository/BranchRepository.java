package com.project.inventory.store.information.repository;

import com.project.inventory.store.information.model.Branch;
import com.project.inventory.store.information.model.GetBranchWithTotalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    @Query(value = "SELECT * from branch where is_deleted = 0", nativeQuery = true)
    List<Branch> findAllIsDeleted();

    Optional<Branch> findByBranch( String location);

    Branch findProductsByBranch( String location);

    @Modifying
    @Query(value = "SELECT branch.id, branch,  COUNT(p.id) totalProduct " +
            "FROM branch as branch " +
            "LEFT JOIN product as p " +
            "ON branch.id = p.branch_id " +
            "where is_deleted = 0 " +
            "GROUP BY branch.id", nativeQuery = true)
    List<GetBranchWithTotalProduct> countProductByBranchId();


}
