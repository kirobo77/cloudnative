package com.example.cloudnative.usersws.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {

		log.info("STATUS : {}", response.status());

		switch (response.status()) {
		case 400:
			break;
		case 404:
			if (methodKey.contains("getOrders")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()),
						"order_service.exception.order_is_empty");
			}
			break;
		default:
			return new Exception(response.reason());
		}
		return null;
	}

}