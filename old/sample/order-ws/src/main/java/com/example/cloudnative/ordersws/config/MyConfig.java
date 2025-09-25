package com.example.cloudnative.ordersws.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties("com.test")
@RefreshScope
@Component
@Data
public class MyConfig {

    private String profile;
    private String region;

}