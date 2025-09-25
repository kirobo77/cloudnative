package com.example.cloudnative.ordersws.messagemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.cloudnative.ordersws.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public OrderDto send(String kafkaTopic, OrderDto orderDto) {
	    	    
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(orderDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	    kafkaTemplate.send(kafkaTopic, jsonInString);
	    
	    log.info("Kafka Producer send data from the Order-ms: " + orderDto);
	    
	    return orderDto;
	}
}