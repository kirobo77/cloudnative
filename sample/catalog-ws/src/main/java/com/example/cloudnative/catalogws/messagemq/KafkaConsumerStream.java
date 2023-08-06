package com.example.cloudnative.catalogws.messagemq;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerStream {

	@Autowired
	CatalogRepository repository;

	@Autowired
	CacheManager cacheManager;

	@Bean
	public Consumer<Message<String>> catalogUpdate() {
		return ((msg) -> {
			log.info("catalogUpdate 이벤트 수신: {}", msg);
			try {
				Map<Object, Object> map = new HashMap<>();
				ObjectMapper mapper = new ObjectMapper();
				try {
					map = mapper.readValue(msg.getPayload(), new TypeReference<Map<Object, Object>>() {
					});
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				CatalogEntity entity = repository.findByProductId((String) map.get("productId"));
				entity.setStock(entity.getStock() - (Integer) map.get("qty"));
				log.info("catalogUpdate entity: {}", entity);
				repository.save(entity);

				evictAllCaches();

				Acknowledgment acknowledgment = msg.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
				if (acknowledgment != null) {
					acknowledgment.acknowledge();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		});
	}

	public void evictAllCaches() {
		cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
		log.info("cache delete");
	}
}
