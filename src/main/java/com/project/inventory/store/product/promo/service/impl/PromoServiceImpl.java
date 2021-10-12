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
    public void savePromo( PromoRequest promoRequest ) throws Exception {
        Promo getPromo = promoRepository.findByProductIdAndStatusOngoingOrUnscheduled( promoRequest.getProductId() );
        if(getPromo == null){
            SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
            Promo promo = new Promo();
            promo.setPercentage( promoRequest.getPercentage() );
            promo.setQuantity( promoRequest.getQuantity() );
            promo.setStartDate( pattern.parse( promoRequest.getStartDate() ) );
            promo.setEndDate( pattern.parse( promoRequest.getEndDate() ) );
            Product product = productService.getProductById( promoRequest.getProductId() );
            promo.setProduct( product );
            promo.setStatus( checkSchedulePromo( promo ) );
            promoRepository.save( promo );
        }else{
            throw new Exception("There is already Promo with this Product. "+ promoRequest.getProductId());
        }

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
        return promoRepository.save( savedPromo );
    }

    @Override
    public void deletePromo( int promoId ) throws Exception {
        try {
            promoRepository.deleteById( promoId );
        } catch( Exception e ) {
            throw new Exception( "UNABLE TO DELETE." + e.getMessage() );
        }
    }

    @Override
    public List<PromoDto> getPromos() throws ParseException {
        List<PromoDto> promos = new ArrayList<>();
        for( Promo promo : promoRepository.findAll() ) {
            promo.setStatus( checkSchedulePromo( promo ) );

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
    public Promo getPromoByProductId( int productId, String status ) {
        return promoRepository.findPromoByProductId(productId, status);
    }

    @Override
    public PromoDto convertEntityToDto( Promo promo ) {
        return mapper.map( promo, PromoDto.class );
    }

    @Override
    public PromoStatus checkSchedulePromo( Promo promo ) throws ParseException {
        SimpleDateFormat pattern = new SimpleDateFormat( "dd-MMM-yyyy HH:mm:ss" );
        Date current = new Date();

        if( pattern.parse( pattern.format( current ) ).before( pattern.parse( pattern.format( promo.getStartDate() ) ) ) ) {
            if( pattern.parse( pattern.format( current ) ).before( pattern.parse( pattern.format( promo.getEndDate() ) ) ) ) {
                return PromoStatus.UNSCHEDULED;
            }
        } else if( pattern.parse( pattern.format( current ) ).after( pattern.parse( pattern.format( promo.getStartDate() ) ) ) ) {
            if( pattern.parse( pattern.format( current ) ).before( pattern.parse( pattern.format( promo.getEndDate() ) ) ) ) {
                return PromoStatus.ONGOING;
            } else if( pattern.parse( pattern.format( current ) ).after( pattern.parse( pattern.format( promo.getEndDate() ) ) ) ) {
                return PromoStatus.END;

            }
        }
        return null;
    }

    @Override
    public Promo getPromoByProductIdAndStatusOngoingOrStatusUnscheduled( int productId ) {
        return promoRepository.findByProductIdAndStatusOngoingOrUnscheduled(productId);
    }
    @Override
    public double getDiscount(Product product) {
        Promo promo = product.getPromo();
        int percentage = 0;
        double discount = 0.0;
        if ( promo != null ) {
            if ( promo.getStatus().name().equals( "ONGOING" ) ) {
                percentage = promo.getPercentage();
                discount = (product.getPrice() * percentage) / 100;
            }
        }
        return discount;
    }

    @Override
    public void setSoldItems( int promoId, int quantity ) {
        Promo promo = getPromo( promoId );

        promo.setSoldQuantity( promo.getSoldQuantity()+quantity );
        promo.setQuantity( promo.getQuantity() - quantity );
        promoRepository.save( promo );
    }
}
