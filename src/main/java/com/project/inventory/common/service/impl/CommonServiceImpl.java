package com.project.inventory.common.service.impl;

import com.project.inventory.common.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CommonServiceImpl implements CommonService {


    @Override
    public String generateStrings() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk";
        Random rnd = new Random();
        StringBuilder randomString = new StringBuilder( 5 );
        for ( int i = 0; i < 5; i++ ) {
            randomString.append( chars.charAt( rnd.nextInt( chars.length() ) ) );
        }
        return randomString.toString();
    }
}
