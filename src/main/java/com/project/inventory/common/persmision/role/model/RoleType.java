package com.project.inventory.common.persmision.role.model;

import java.io.Serializable;

public enum RoleType implements Serializable{

    // owner is the business owner or the seller
    // staff is the employee of the business owner
    // customer is the buyer or consumer

    CUSTOMER ( "CUSTOMER" ),
    STAFF ( "STAFF" ),
    OWNER ( "OWNER" ),
    USER ( "USER" );

    String accountRoleType;

    private RoleType (String accountRoleType){
        this.accountRoleType = accountRoleType;
    }

    @Override
    public String toString() {
        return this.accountRoleType;
    }
}
