package com.project.inventory.store.shipping.service;

import com.project.inventory.store.shipping.model.ShippingFee;

import java.util.List;

public interface ShippingFeeService {
    List<ShippingFee> getShippingFees();

    ShippingFee getShippingFeeById( int id );

    void saveShippingFee( ShippingFee shippingFee );

    void updateShippingFee( int id, ShippingFee shippingFee );
}
