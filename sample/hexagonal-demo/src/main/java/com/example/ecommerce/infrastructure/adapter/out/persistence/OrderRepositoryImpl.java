// src/main/java/com/example/ecommerce/infrastructure/adapter/out/persistence/OrderRepositoryImpl.java
package com.example.ecommerce.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.ecommerce.application.port.out.OrderRepository;
import com.example.ecommerce.domain.model.Order;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpa;

    public OrderRepositoryImpl(OrderJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Order save(Order order) {
        return jpa.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpa.findById(id);
    }
}
