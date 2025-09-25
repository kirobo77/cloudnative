// src/main/java/com/example/ecommerce/infrastructure/adapter/out/external/PaymentServiceImpl.java
package com.example.ecommerce.infrastructure.adapter.out.external;

import java.math.BigDecimal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ecommerce.application.port.out.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public PaymentResult processPayment(Long orderId, BigDecimal amount) {
        // 데모: 항상 승인 처리
        String txId = UUID.randomUUID().toString();
        log.info("Payment approved: orderId={}, amount={}, tx={}", orderId, amount, txId);
        return new PaymentResult(true, txId);
    }
}
