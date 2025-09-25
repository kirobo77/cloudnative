// src/main/java/com/example/ecommerce/infrastructure/adapter/out/external/NotificationServiceImpl.java
package com.example.ecommerce.infrastructure.adapter.out.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ecommerce.application.port.out.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void notifyOrderCreated(Long orderId) {
        log.info("Notify: Order created (orderId={})", orderId);
    }

    @Override
    public void notifyOrderPaid(Long orderId) {
        log.info("Notify: Order paid (orderId={})", orderId);
    }
}
