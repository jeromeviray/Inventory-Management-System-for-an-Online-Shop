package com.project.inventory.store.information.model;

public class GetStoreBranchDto {
    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch( String branch ) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "GetStoreBranchDto{" +
                "branch='" + branch + '\'' +
                '}';
    }
}
