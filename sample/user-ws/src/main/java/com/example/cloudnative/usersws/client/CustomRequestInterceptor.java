package com.example.cloudnative.usersws.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		log.info("URL : {}", template.url());

	}

}
