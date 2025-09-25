package com.example.order.client.circuitbreaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryFeignConfig {
  @Bean
  public feign.Retryer retryer() {
    return new feign.Retryer.Default(100, 1000, 3);
  }
  
  @Bean
  public feign.codec.ErrorDecoder errorDecoder() {
    return (methodKey, response) -> {
      feign.codec.ErrorDecoder defaultDecoder = new feign.codec.ErrorDecoder.Default();
      Exception ex = defaultDecoder.decode(methodKey, response);
      if (ex instanceof feign.RetryableException) return ex;
      if (response.status() >= 500) {
        return new feign.RetryableException(
            response.status(), "server error, retry", response.request().httpMethod(),
            100L, response.request());
      }
      return ex;
    };
  }  
}
