package com.example.cloudnative.catalogws.service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
