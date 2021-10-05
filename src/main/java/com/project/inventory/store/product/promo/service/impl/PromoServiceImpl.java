package com.project.inventory.store.product.promo.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoStatus;
import com.project.inventory.store.product.promo.repository.PromoRepository;
import com.project.inventory.store.product.promo.service.PromoService;
import com.project.inventory.store.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {

    Logger logger = LoggerFactory.getLogger( PromoServiceImpl.class );

    @Autowired
    private PromoRepository promoRepository;
    @Autowired
    private ProductService productService;

    @Override
    public void savePromo( int productId, Promo promo ) {
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
        Date current = new Date();

//        promo.setStatus(
//                pattern.format( current ).equals( pattern.format( promo.getStartDate() ) ) ?
//                        PromoStatus.ONGOING : PromoStatus.UNSCHEDULED
//        );

            Product product = productService.getProductById( productId );
        promo.setProduct( product );
        promoRepository.save( promo );
    }

    @Override
    public void updatePromo( int promoId, Promo promo ) {
        Promo savedPromo = getPromo( promoId );
        savedPromo.setPercentage( promo.getPercentage() );
        savedPromo.setProduct( productService.getProductById( promo.getId() ) );
        savedPromo.setQuantity( promo.getQuantity() );
        savedPromo.setSoldQuantity( promo.getSoldQuantity() );
        savedPromo.setStartDate( promo.getStartDate() );
        savedPromo.setEndDate( promo.getEndDate() );

        promoRepository.save( promo );
    }

    @Override
    public void deletePromo( int promoId ) {

    }

    @Override
    public List<Promo> getPromos() {
        return promoRepository.findAll();
    }

    @Override
    public Promo getPromo( int id ) {
        return promoRepository.findById( id ).orElseThrow( () -> new NotFoundException( String.format( "Promo not Found. %s", id ) ) );
    }
}
