package com.example.cloudnative.catalogws.messagemq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {
 
	@Autowired
	CatalogRepository repository;
	
	@KafkaListener(topics="example-kafka-test")
	@CacheEvict(value="catalogs", allEntries=true)
    public void processMessage(String kafkaMessage, Acknowledgment acknowledgment) {
		log.info("kafkaMessage : =====> " + kafkaMessage);

		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
		entity.setStock(entity.getStock() - (Integer)map.get("qty"));

		repository.save(entity);
		
		if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}