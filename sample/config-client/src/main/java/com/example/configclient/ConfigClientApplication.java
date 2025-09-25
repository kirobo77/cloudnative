package com.example.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.configclient.props.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ConfigClientApplication {
 
 public static void main(String[] args) {
     SpringApplication.run(ConfigClientApplication.class, args);
 }
}