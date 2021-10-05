package com.project.inventory.store.product.promo.service;

import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoDto;

import java.util.List;

public interface PromoService {

    void savePromo( int productId, Promo promo );

    Promo updatePromo( int promoId, Promo promo );

    void deletePromo( int promoId );

    List<Promo> getPromos();

    Promo getPromo(int id);

    PromoDto convertEntityToDto(Promo promo);
}
