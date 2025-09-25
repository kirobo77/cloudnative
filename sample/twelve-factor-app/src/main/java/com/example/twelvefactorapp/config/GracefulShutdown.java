package com.example.twelvefactorapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class GracefulShutdown {
    private static final Logger logger = LoggerFactory.getLogger(GracefulShutdown.class);
    
    @PreDestroy
    public void onDestroy() {
        logger.info("Application is shutting down gracefully...");
        // Clean up resources, close connections, etc.
    }
}