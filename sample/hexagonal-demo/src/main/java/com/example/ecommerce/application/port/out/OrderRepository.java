// src/main/java/com/example/ecommerce/application/port/out/OrderRepository.java
package com.example.ecommerce.application.port.out;

import java.util.Optional;

import com.example.ecommerce.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
}
