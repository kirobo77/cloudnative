// src/main/java/com/example/ecommerce/infrastructure/config/ApplicationConfig.java
package com.example.ecommerce.infrastructure.config;

import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.ecommerce")
@EnableJpaRepositories(basePackages = "com.example.ecommerce")
@EntityScan(basePackages = "com.example.ecommerce")
public class ApplicationConfig {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
