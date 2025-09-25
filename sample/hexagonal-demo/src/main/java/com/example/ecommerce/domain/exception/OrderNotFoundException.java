// src/main/java/com/example/ecommerce/domain/exception/OrderNotFoundException.java
package com.example.ecommerce.domain.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found: " + id);
    }
}
