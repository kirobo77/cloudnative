// src/test/java/com/example/ecommerce/domain/service/OrderDomainServiceTest.java
package com.example.ecommerce.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.ecommerce.domain.model.Order;

class OrderDomainServiceTest {

    private final OrderDomainService service = new OrderDomainService();

    private static Order.OrderLine line(long pid, String name, BigDecimal price, int qty) {
        return new Order.OrderLine(pid, name, price, qty);
    }

    @Test
    @DisplayName("유효한 주문은 검증 통과 후 총액 재계산")
    void validate_success_and_recalc() {
        Order order = new Order(1L, List.of(
                line(100L, "Keyboard", new BigDecimal("45000"), 2),
                line(200L, "Mouse", new BigDecimal("15000"), 1)
        ));

        service.validate(order);
        service.recalcTotal(order);

        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal("105000"));
    }

    @Test
    @DisplayName("customerId 누락시 검증 실패")
    void validate_fails_when_customer_missing() {
        Order order = new Order(null, List.of(line(100L, "Keyboard", new BigDecimal("45000"), 1)));

        assertThatThrownBy(() -> service.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("customerId");
    }

    @Test
    @DisplayName("주문 라인이 비어있으면 검증 실패")
    void validate_fails_when_lines_empty() {
        Order order = new Order(1L, List.of());

        assertThatThrownBy(() -> service.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("order lines");
    }

    @Test
    @DisplayName("단가가 음수면 검증 실패")
    void validate_fails_when_unit_price_negative() {
        Order order = new Order(1L, List.of(line(100L, "Keyboard", new BigDecimal("-1"), 1)));

        assertThatThrownBy(() -> service.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unitPrice");
    }

    @Test
    @DisplayName("수량이 0 이하면 검증 실패")
    void validate_fails_when_quantity_non_positive() {
        Order order = new Order(1L, List.of(line(100L, "Keyboard", new BigDecimal("1000"), 0)));

        assertThatThrownBy(() -> service.validate(order))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("quantity");
    }

    @Test
    @DisplayName("recalcTotal은 라인 합계를 정확히 산출")
    void recalc_total_sums_all_lines() {
        Order order = new Order(1L, List.of(
                line(1L, "A", new BigDecimal("10.50"), 3), // 31.50
                line(2L, "B", new BigDecimal("5.25"), 2)   // 10.50
        ));

        service.recalcTotal(order);
        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal("42.00"));
    }
}
