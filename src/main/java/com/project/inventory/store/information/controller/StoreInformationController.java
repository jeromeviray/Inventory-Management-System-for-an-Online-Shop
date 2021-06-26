package com.project.inventory.store.information.controller;

import com.project.inventory.store.information.model.StoreInformation;
import com.project.inventory.store.information.service.StoreInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/store")
public class StoreInformationController {
    @Autowired
    private StoreInformationService storeInformationService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveStoreInformation(@RequestBody StoreInformation storeInformation){
        storeInformationService.saveStoreInformation(storeInformation);
        return new ResponseEntity(HttpStatus.OK);
    }
}
