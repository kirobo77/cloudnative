package com.example.cloudnative.catalogws.controller;



import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudnative.catalogws.config.MyConfig;
import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.model.CatalogResponseModel;
import com.example.cloudnative.catalogws.service.CatalogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/catalog-ms")
public class CatalogController {
    @Autowired
    private Environment env;

    @Autowired
    CatalogService catalogService;
    
    @Autowired
    MyConfig myConfig;
    
    @GetMapping("/")
    public String health() {
    	
    	log.info(myConfig.toString());
        return String.format("Hi, there. I'm a Catalog microservice(%s,%s)",myConfig.getProfile(),myConfig.getRegion());
    }

    @GetMapping(value="/catalogs")
    public ResponseEntity<List<CatalogResponseModel>> getCatalogs() {
    	log.info("getCatalogs");
    	
        Iterable<CatalogEntity> orderList = catalogService.getAllCatalogs();

        List<CatalogResponseModel> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, CatalogResponseModel.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
