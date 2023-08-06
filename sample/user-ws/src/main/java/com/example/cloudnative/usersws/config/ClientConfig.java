package com.example.cloudnative.usersws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.cloudnative.usersws.client.CustomDecoder;
import com.example.cloudnative.usersws.client.CustomErrorDecoder;
import com.example.cloudnative.usersws.client.CustomRequestInterceptor;

import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

@Configuration
public class ClientConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new CustomRequestInterceptor();
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
	
    @Bean
    public Decoder decoder() {
        return new CustomDecoder();
    }
}
