// src/main/java/com/example/ecommerce/infrastructure/adapter/out/persistence/OrderJpaRepository.java
package com.example.ecommerce.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.domain.model.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
