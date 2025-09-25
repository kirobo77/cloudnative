package com.example.order.api;

import com.example.order.client.InventoryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order2")
public class OrderController {

  private final InventoryClient inventoryClient;

  public OrderController(InventoryClient inventoryClient) {
    this.inventoryClient = inventoryClient;
  }

  @GetMapping("/stock/{sku}")
  public Map<String, Object> checkStock(@PathVariable String sku) {
    Map<String, Object> stock = inventoryClient.getStock(sku);
    return Map.of(
        "sku", stock.get("sku"),
        "quantity", stock.get("quantity"),
        "viaInstance", stock.get("instanceId")
    );
  }
}
