package com.example.demogateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.demogateway.transform.RequestBodyTransform;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RewriteGlobalFilter implements GlobalFilter, Ordered {
	
    @Autowired
    private ModifyRequestBodyGatewayFilterFactory modifyRequestBodyFilter;
    
    @Autowired
    private RequestBodyTransform requestBodyTransform;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("===================== pre filter =====================");
        
		return modifyRequestBodyFilter
				.apply(new ModifyRequestBodyGatewayFilterFactory
						.Config()
						.setRewriteFunction(String.class, String.class, requestBodyTransform))
				.filter(exchange, chain)
				.then(Mono.fromRunnable(() -> {
                   log.info("===================== post filter =====================");
               }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}