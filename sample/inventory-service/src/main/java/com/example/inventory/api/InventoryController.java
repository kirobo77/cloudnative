// inventory-service/src/main/java/com/example/inventory/api/InventoryController.java (수정)
package com.example.inventory.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

  @Value("${spring.application.name}")
  private String appName;

  @Value("${server.port}")
  private int port;

  private final Random random = new Random();

  @GetMapping("/{sku}")
  public Map<String, Object> getStock(@PathVariable String sku) throws Exception {
    String host = InetAddress.getLocalHost().getHostAddress();
    int quantity = (port % 2 == 0) ? 50 : 100;
    return Map.of(
        "sku", sku,
        "quantity", quantity,
        "instanceId", appName + ":" + host + ":" + port
    );
  }

  // 지연 시뮬레이션 엔드포인트
  @GetMapping("/{sku}/slow")
  public Map<String, Object> getStockSlow(@PathVariable String sku) throws Exception {
    // 1-3초 랜덤 지연
    Thread.sleep(1000 + random.nextInt(2000));
    return getStock(sku);
  }

  // 실패 시뮬레이션 엔드포인트 (50% 확률로 실패)
  @GetMapping("/{sku}/unreliable")
  public Map<String, Object> getStockUnreliable(@PathVariable String sku) throws Exception {
    if (random.nextBoolean()) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
          "Simulated service failure");
    }
    return getStock(sku);
  }
}
