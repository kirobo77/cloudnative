package com.example.cloudnative.usersws.client;

import java.io.IOException;
import java.lang.reflect.Type;

import feign.Response;
import feign.Util;
import feign.codec.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDecoder extends StringDecoder {

	@Override
	public Object decode(Response response, Type type) throws IOException {
		
		log.info("DECODE : {}", response.toString());
		
		if (response.status() == 404 || response.status() == 204)
			return Util.emptyValueOf(type);
		if (response.body() == null)
			return null;
		if (byte[].class.equals(type)) {
			return Util.toByteArray(response.body().asInputStream());
		}
		return super.decode(response, type);
	}

}
