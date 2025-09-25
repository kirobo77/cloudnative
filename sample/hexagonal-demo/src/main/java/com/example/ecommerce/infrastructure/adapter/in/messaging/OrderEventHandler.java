// src/main/java/com/example/ecommerce/infrastructure/adapter/in/messaging/OrderEventHandler.java
package com.example.ecommerce.infrastructure.adapter.in.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 데모용 Event Handler (실제 발행은 생략, 필요시 ApplicationEventPublisher로 OrderCreatedEvent 발행)
 */
@Component
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    @EventListener(OrderCreatedEvent.class)
    public void onCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: orderId={}", event.orderId());
    }

    public record OrderCreatedEvent(Long orderId) {}
}
