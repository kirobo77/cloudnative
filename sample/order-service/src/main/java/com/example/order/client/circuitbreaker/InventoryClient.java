//order-service/src/main/java/com/example/order/client/circuitbreaker/InventoryClient.java

package com.example.order.client.circuitbreaker;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "inventory-service", path = "/api/inventory", fallbackFactory = InventoryClientFallbackFactory.class, configuration = InventoryFeignConfig.class)
public interface InventoryClient {

	@GetMapping("/{sku}")
	@CircuitBreaker(name = "inventoryClient", fallbackMethod = "getStockFallback")
	@Bulkhead(name = "inventoryClient", type = Bulkhead.Type.SEMAPHORE)
	Map<String, Object> getStock(@PathVariable("sku") String sku);

	// Fallback 메서드 
	default Map<String, Object> getStockFallback(String sku, Exception ex) {
		return Map.of("sku", sku, "quantity", 0, "instanceId", "fallback-response", "error",
				"Service temporarily unavailable: " + ex.getMessage());
	}	
}
