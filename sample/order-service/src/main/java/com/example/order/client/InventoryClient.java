package com.example.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "inventory-service2", path = "/api/inventory")
public interface InventoryClient {
  @GetMapping("/{sku}")
  Map<String, Object> getStock(@PathVariable("sku") String sku);
}
