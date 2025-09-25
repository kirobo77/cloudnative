// src/main/java/com/example/ecommerce/domain/service/OrderDomainService.java
package com.example.ecommerce.domain.service;

import java.math.BigDecimal;

import com.example.ecommerce.domain.model.Order;

public class OrderDomainService {

    public void validate(Order order) {
        if (order.getCustomerId() == null) {
            throw new IllegalArgumentException("customerId is required");
        }
        if (order.getLines() == null || order.getLines().isEmpty()) {
            throw new IllegalArgumentException("order lines are required");
        }
        order.getLines().forEach(line -> {
            if (line.getQuantity() <= 0) {
                throw new IllegalArgumentException("quantity must be > 0");
            }
            if (line.getUnitPrice() == null || line.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("unitPrice must be >= 0");
            }
        });
    }

    public void recalcTotal(Order order) {
        order.recalculateTotal();
    }
}
