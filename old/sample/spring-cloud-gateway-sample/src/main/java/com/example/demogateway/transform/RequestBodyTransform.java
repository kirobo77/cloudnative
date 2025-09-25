package com.example.demogateway.transform;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestBodyTransform implements RewriteFunction<String,String> {
  
    @Override
    public Publisher<String> apply(ServerWebExchange exchange, String requestBody) {

        String modifiedJsonBody= "modifiedJsonBody";
    	if(StringUtils.isNotEmpty(requestBody)) {
    		modifiedJsonBody= "modifiedJsonBody=".concat(requestBody);
    	}
        log.info("RequestBodyTransform : {}", modifiedJsonBody);
    	return Mono.just(modifiedJsonBody);
        
    }

}