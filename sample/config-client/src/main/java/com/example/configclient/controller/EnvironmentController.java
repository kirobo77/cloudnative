package com.example.configclient.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configclient.props.AppProperties;


@RestController
@RefreshScope
public class EnvironmentController {
    
    @Value("${spring.profiles.active:default}")
    private String activeProfile;
    
    @Value("${app.environment.name:unknown}")
    private String environmentName;
    
    private final AppProperties appProperties;
    private final Environment environment;
    
    public EnvironmentController(AppProperties appProperties, Environment environment) {
        this.appProperties = appProperties;
        this.environment = environment;
    }
    
    @GetMapping("/environment")
    public Map<String, Object> getEnvironmentInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("activeProfile", activeProfile);
        info.put("environmentName", environmentName);
        info.put("appName", appProperties.getName());
        info.put("appVersion", appProperties.getVersion());
        info.put("databaseConfig", getDatabaseConfig());
        info.put("securityConfig", getSecurityConfig());
        return info;
    }
    
    @GetMapping("/properties")
    public Map<String, String> getAllProperties() {
        Map<String, String> properties = new HashMap<>();
        
        // 특정 prefix로 시작하는 모든 속성 조회
        MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        
        properties.put("server.port", environment.getProperty("server.port"));
        properties.put("spring.application.name", environment.getProperty("spring.application.name"));
        properties.put("logging.level.com.example", environment.getProperty("logging.level.com.example"));
        
        return properties;
    }
    
    private Map<String, Object> getDatabaseConfig() {
        Map<String, Object> dbConfig = new HashMap<>();
        dbConfig.put("maxConnections", appProperties.getDatabase().getMaxConnections());
        dbConfig.put("connectionTimeout", appProperties.getDatabase().getConnectionTimeout());
        return dbConfig;
    }
    
    private Map<String, Object> getSecurityConfig() {
        Map<String, Object> secConfig = new HashMap<>();
        secConfig.put("jwtExpirationSeconds", appProperties.getSecurity().getJwtExpirationSeconds());
        // JWT Secret은 보안상 노출하지 않음
        secConfig.put("jwtSecretConfigured", 
            appProperties.getSecurity().getJwtSecret() != null && 
            !appProperties.getSecurity().getJwtSecret().isEmpty());
        return secConfig;
    }
}