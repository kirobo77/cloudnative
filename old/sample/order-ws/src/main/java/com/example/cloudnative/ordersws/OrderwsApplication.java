package com.example.cloudnative.ordersws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderwsApplication {
    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(OrderwsApplication.class, args);
    }


}
