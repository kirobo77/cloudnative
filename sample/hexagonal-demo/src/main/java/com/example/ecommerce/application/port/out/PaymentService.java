// src/main/java/com/example/ecommerce/application/port/out/PaymentService.java
package com.example.ecommerce.application.port.out;

import java.math.BigDecimal;

public interface PaymentService {

    PaymentResult processPayment(Long orderId, BigDecimal amount);

    record PaymentResult(boolean success, String transactionId) {}
}
