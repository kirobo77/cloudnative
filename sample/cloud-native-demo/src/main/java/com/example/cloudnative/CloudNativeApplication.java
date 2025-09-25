package com.example.cloudnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CloudNativeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudNativeApplication.class, args);
    }
}

@RestController
class HelloController {
    
    @GetMapping("/")
    public String hello() {
        return "Hello Cloud Native World!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Application is running";
    }
}