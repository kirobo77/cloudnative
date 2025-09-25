package com.example.twelvefactorapp.service;

import com.example.twelvefactorapp.entity.Product;
import com.example.twelvefactorapp.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        logger.info("Fetching product by id: {}", id);
        return productRepository.findById(id);
    }
    
    public Product createProduct(Product product) {
        logger.info("Creating new product: {}", product.getName());
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, Product product) {
        logger.info("Updating product: {}", id);
        product.setId(id);
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        logger.info("Deleting product: {}", id);
        productRepository.deleteById(id);
    }
    
    @Async
    public CompletableFuture<List<Product>> searchProductsAsync(String name) {
        logger.info("Async search for products with name: {}", name);
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return CompletableFuture.completedFuture(products);
    }
}