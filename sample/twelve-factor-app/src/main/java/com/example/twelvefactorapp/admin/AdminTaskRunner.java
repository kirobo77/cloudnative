package com.example.twelvefactorapp.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.twelvefactorapp.entity.Product;
import com.example.twelvefactorapp.repository.ProductRepository;

@Component
public class AdminTaskRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AdminTaskRunner.class);
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // 초기 데이터 설정 등의 관리 작업
        if (args.length > 0 && "init-data".equals(args[0])) {
            logger.info("Initializing sample data...");
            initializeSampleData();
        }
    }
    
    private void initializeSampleData() {
        if (productRepository.count() == 0) {
            productRepository.save(new Product("Laptop", "High-performance laptop", 1500.00));
            productRepository.save(new Product("Mouse", "Wireless optical mouse", 25.00));
            productRepository.save(new Product("Keyboard", "Mechanical keyboard", 120.00));
            logger.info("Sample data initialized successfully");
        }
    }
}