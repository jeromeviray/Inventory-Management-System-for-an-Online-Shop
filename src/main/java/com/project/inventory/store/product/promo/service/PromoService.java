package com.project.inventory.store.product.promo.service;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoDto;
import com.project.inventory.store.product.promo.model.PromoRequest;
import com.project.inventory.store.product.promo.model.PromoStatus;

import java.text.ParseException;
import java.util.List;

public interface PromoService {

    void savePromo( PromoRequest promoRequest ) throws Exception;

    Promo updatePromo( int promoId, Promo promo );

    void deletePromo( int promoId ) throws Exception;

    List<PromoDto> getPromos() throws ParseException;

    Promo getPromo( int id );

    Promo getPromoByProductId( int productId, String status );

    PromoDto convertEntityToDto( Promo promo );

    PromoStatus checkSchedulePromo( Promo promo ) throws ParseException;

    Promo getPromoByProductIdAndStatusOngoingOrStatusUnscheduled( int productId );

    double getDiscount( Product product );

    void setSoldItems(int promoId, int quantity);
}
