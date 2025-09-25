// src/main/java/com/example/ecommerce/application/port/in/CreateOrderUseCase.java
package com.example.ecommerce.application.port.in;

import java.math.BigDecimal;
import java.util.List;

public interface CreateOrderUseCase {

    Long createOrder(CreateOrderCommand command);

    record CreateOrderCommand(Long customerId, List<Item> items) {}

    record Item(Long productId, String productName, BigDecimal unitPrice, int quantity) {}
}
