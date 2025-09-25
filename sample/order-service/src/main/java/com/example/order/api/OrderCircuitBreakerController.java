// order-service/src/main/java/com/example/order/api/OrderController.java
package com.example.order.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.client.circuitbreaker.InventoryClient;

@RestController
@RequestMapping("/api/order")
public class OrderCircuitBreakerController {

  private final InventoryClient inventoryClient;

  public OrderCircuitBreakerController(InventoryClient inventoryClient) {
    this.inventoryClient = inventoryClient;
  }

  // 기존 동기 호출 (회복력 패턴 적용)
  @GetMapping("/stock/{sku}")
  public Map<String, Object> checkStock(@PathVariable String sku) {
    Map<String, Object> stock = inventoryClient.getStock(sku);
    return Map.of(
        "sku", stock.get("sku"),
        "quantity", stock.get("quantity"),
        "viaInstance", stock.get("instanceId"),
        "error", stock.getOrDefault("error", "none")
    );
  }

}
