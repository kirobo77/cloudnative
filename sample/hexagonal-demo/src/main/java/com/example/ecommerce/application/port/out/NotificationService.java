// src/main/java/com/example/ecommerce/application/port/out/NotificationService.java
package com.example.ecommerce.application.port.out;

public interface NotificationService {
    void notifyOrderCreated(Long orderId);
    void notifyOrderPaid(Long orderId);
}
