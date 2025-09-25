// src/main/java/com/example/ecommerce/application/port/in/GetOrderUseCase.java
package com.example.ecommerce.application.port.in;

import com.example.ecommerce.domain.model.Order;

public interface GetOrderUseCase {
    Order getOrder(Long orderId);
}
