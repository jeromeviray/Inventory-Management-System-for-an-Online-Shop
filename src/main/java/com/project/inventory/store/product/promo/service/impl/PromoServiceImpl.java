package com.project.inventory.store.product.promo.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.model.PromoDto;
import com.project.inventory.store.product.promo.model.PromoRequest;
import com.project.inventory.store.product.promo.model.PromoStatus;
import com.project.inventory.store.product.promo.repository.PromoRepository;
import com.project.inventory.store.product.promo.service.PromoService;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {

    Logger logger = LoggerFactory.getLogger( PromoServiceImpl.class );

    @Autowired
    private PromoRepository promoRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;

    @Override
    public void savePromo( PromoRequest promoRequest ) throws ParseException {
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
        Date current = new Date();
        Promo promo = new Promo();
        int compared = pattern.format(pattern.parse( pattern.format( current ) )).compareTo(pattern.format(pattern.parse( promoRequest.getStartDate() )));
        if( compared > 0 ) {
            promo.setStatus( PromoStatus.ONGOING );
        } else if( compared < 0 ) {
            promo.setStatus( PromoStatus.UNSCHEDULED );
        } else {
            promo.setStatus( PromoStatus.ONGOING );
        }
        promo.setPercentage( promoRequest.getPercentage() );
        promo.setQuantity( promoRequest.getQuantity() );
        promo.setStartDate( pattern.parse( promoRequest.getStartDate() ) );
        promo.setEndDate( pattern.parse( promoRequest.getEndDate() ) );
        Product product = productService.getProductById( promoRequest.getProductId() );
        promo.setProduct( product );
        promoRepository.save( promo );
    }

    @Override
    public Promo updatePromo( int promoId, Promo promo ) {
        Promo savedPromo = getPromo( promoId );
        savedPromo.setPercentage( promo.getPercentage() );
        savedPromo.setProduct( productService.getProductById( promo.getProduct().getId() ) );
        savedPromo.setQuantity( promo.getQuantity() );
        savedPromo.setSoldQuantity( promo.getSoldQuantity() );
        savedPromo.setStartDate( promo.getStartDate() );
        savedPromo.setEndDate( promo.getEndDate() );

        return promoRepository.save( promo );
    }

    @Override
    public void deletePromo( int promoId ) {

    }

    @Override
    public List<PromoDto> getPromos() throws ParseException {
        List<PromoDto> promos = new ArrayList<>();
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );

        for( Promo promo : promoRepository.findAll()) {
            Date current = new Date();
            int startDate = pattern.format( pattern.parse( pattern.format(promo.getStartDate()) ) ).compareTo(pattern.format( pattern.parse( pattern.format( current ) ) ));
            int endDate = pattern.format( pattern.parse( pattern.format(promo.getEndDate()) ) ).compareTo(pattern.format( pattern.parse( pattern.format( current ) ) ));
            if(startDate > 0){
                if(endDate < 0){
                    promo.setStatus( PromoStatus.END );
                }else {
                    promo.setStatus( PromoStatus.ONGOING );
                }
            }
            if(endDate < 0){
                promo.setStatus( PromoStatus.END );
            }else {
                promo.setStatus( PromoStatus.ONGOING );
            }
            updatePromo( promo.getId(), promo );
            promos.add( convertEntityToDto( promo ) );
        }
        return promos;
    }

    @Override
    public Promo getPromo( int id ) {
        return promoRepository.findById( id ).orElseThrow( () -> new NotFoundException( String.format( "Promo not Found. %s", id ) ) );
    }

    @Override
    public PromoDto convertEntityToDto( Promo promo ) {
        return mapper.map( promo, PromoDto.class );
    }
}
