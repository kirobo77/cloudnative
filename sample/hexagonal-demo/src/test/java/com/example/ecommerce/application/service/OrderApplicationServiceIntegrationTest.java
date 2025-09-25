// src/test/java/com/example/ecommerce/application/service/OrderApplicationServiceIntegrationTest.java
package com.example.ecommerce.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.application.port.in.CreateOrderUseCase;
import com.example.ecommerce.application.port.in.CreateOrderUseCase.CreateOrderCommand;
import com.example.ecommerce.application.port.in.CreateOrderUseCase.Item;
import com.example.ecommerce.application.port.in.GetOrderUseCase;
import com.example.ecommerce.domain.model.Order;
import com.example.ecommerce.infrastructure.config.ApplicationConfig;

@SpringBootTest(classes = ApplicationConfig.class) 
@Transactional
class OrderApplicationServiceIntegrationTest {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private GetOrderUseCase getOrderUseCase;

    @Test
    @DisplayName("주문 생성 후 조회하면 상태가 PAID이며 총액이 일치한다")
    void create_and_get_order_paid() {
        // Given
        CreateOrderCommand cmd = new CreateOrderCommand(
                777L,
                List.of(new Item(500L, "Notebook", new BigDecimal("25.00"), 4)) // 100.00
        );

        // When
        Long id = createOrderUseCase.createOrder(cmd);
        Order order = getOrderUseCase.getOrder(id);

        // Then
        assertThat(order.getId()).isEqualTo(id);
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PAID);
        assertThat(order.getTotalPrice()).isEqualByComparingTo("100.00");
    }
}
