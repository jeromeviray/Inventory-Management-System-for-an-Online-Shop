package com.project.inventory.store.product.promo.service;

import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoDto;
import com.project.inventory.store.product.promo.model.PromoRequest;

import java.text.ParseException;
import java.util.List;

public interface PromoService {

    void savePromo( PromoRequest promoRequest ) throws ParseException;

    Promo updatePromo( int promoId, Promo promo );

    void deletePromo( int promoId );

    List<PromoDto> getPromos() throws ParseException;

    Promo getPromo(int id);

    PromoDto convertEntityToDto(Promo promo);
}
