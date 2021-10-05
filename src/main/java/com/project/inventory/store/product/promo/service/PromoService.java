package com.project.inventory.store.product.promo.service;

import com.project.inventory.store.product.promo.model.Promo;

import java.util.List;

public interface PromoService {

    void savePromo( int productId, Promo promo );

    void updatePromo( int promoId, Promo promo );

    void deletePromo( int promoId );

    List<Promo> getPromos();

    Promo getPromo(int id);
}
