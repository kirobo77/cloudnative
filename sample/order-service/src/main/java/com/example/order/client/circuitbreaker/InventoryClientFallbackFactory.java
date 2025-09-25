//order-service/src/main/java/com/example/order/client/circuitbreaker/InventoryClientFallbackFactory.java
package com.example.order.client.circuitbreaker;

import java.util.Map;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class InventoryClientFallbackFactory implements FallbackFactory<InventoryClient> {

	@Override
	public InventoryClient create(Throwable cause) {
		return new InventoryClient() {
			@Override
			public Map<String, Object> getStock(String sku) {
				return Map.of("sku", sku, "quantity", 0, "instanceId", "fallback-factory", "error",
						"Inventory service is down: " + cause.getMessage(), "fallbackReason",
						cause.getClass().getSimpleName());
			}
		};
	}
}
