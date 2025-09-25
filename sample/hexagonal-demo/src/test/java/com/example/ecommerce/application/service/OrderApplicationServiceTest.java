// src/test/java/com/example/ecommerce/application/service/OrderApplicationServiceTest.java
package com.example.ecommerce.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.ecommerce.application.port.in.CreateOrderUseCase;
import com.example.ecommerce.application.port.out.NotificationService;
import com.example.ecommerce.application.port.out.OrderRepository;
import com.example.ecommerce.application.port.out.PaymentService;
import com.example.ecommerce.domain.model.Order;

@ExtendWith(MockitoExtension.class)
class OrderApplicationServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderApplicationService orderApplicationService;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @Test
    @DisplayName("createOrder: 결제 성공 시 상태 PAID로 저장되고 알림이 발송된다")
    void createOrder_success_paid_and_notify() {
        // Given: 입력 커맨드
        CreateOrderUseCase.CreateOrderCommand cmd = new CreateOrderUseCase.CreateOrderCommand(
                10L,
                List.of(new CreateOrderUseCase.Item(100L, "Keyboard", new BigDecimal("50.00"), 2))
        );

        // save 동작: 첫 저장 시 ID 부여를 시뮬레이션
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            if (o.getId() == null) {
                ReflectionTestUtils.setField(o, "id", 1L);
            }
            return o;
        });

        // 결제 성공
        when(paymentService.processPayment(anyLong(), any(BigDecimal.class)))
                .thenReturn(new PaymentService.PaymentResult(true, "TX-1"));

        // When
        Long orderId = orderApplicationService.createOrder(cmd);

        // Then: ID 반환, 결제 호출, 두 번 저장(생성/상태전이), 알림 호출
        assertThat(orderId).isEqualTo(1L);
        verify(paymentService).processPayment(eq(1L), eq(new BigDecimal("100.00")));
        verify(orderRepository, atLeast(2)).save(orderCaptor.capture());
        verify(notificationService).notifyOrderCreated(1L);
        verify(notificationService).notifyOrderPaid(1L);

        // 두 번째 저장된 엔티티가 PAID인지 확인
        Order lastSaved = orderCaptor.getAllValues().get(orderCaptor.getAllValues().size() - 1);
        assertThat(lastSaved.getStatus()).isEqualTo(Order.OrderStatus.PAID);
        assertThat(lastSaved.getTotalPrice()).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("createOrder: 결제 실패 시 상태는 CREATED 유지, 결제 알림 미발송")
    void createOrder_payment_failed_keeps_created() {
        // Given
        CreateOrderUseCase.CreateOrderCommand cmd = new CreateOrderUseCase.CreateOrderCommand(
                20L,
                List.of(new CreateOrderUseCase.Item(200L, "Mouse", new BigDecimal("30.00"), 1))
        );

        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            if (o.getId() == null) {
                ReflectionTestUtils.setField(o, "id", 2L);
            }
            return o;
        });

        when(paymentService.processPayment(anyLong(), any(BigDecimal.class)))
                .thenReturn(new PaymentService.PaymentResult(false, "TX-FAIL"));

        // When
        Long orderId = orderApplicationService.createOrder(cmd);

        // Then
        assertThat(orderId).isEqualTo(2L);
        verify(paymentService).processPayment(eq(2L), eq(new BigDecimal("30.00")));
        verify(orderRepository, atLeast(1)).save(orderCaptor.capture());
        verify(notificationService).notifyOrderCreated(2L);
        verify(notificationService, never()).notifyOrderPaid(anyLong());

        // 마지막 저장 엔티티 상태는 CREATED
        Order lastSaved = orderCaptor.getAllValues().get(orderCaptor.getAllValues().size() - 1);
        assertThat(lastSaved.getStatus()).isEqualTo(Order.OrderStatus.CREATED);
    }

    @Test
    @DisplayName("getOrder: 존재 시 반환, 미존재 시 예외 발생")
    void getOrder_found_else_throw() {
        // Given
        Order existing = new Order(30L, List.of(
                new Order.OrderLine(300L, "Pad", new BigDecimal("10.00"), 3)
        ));
        ReflectionTestUtils.setField(existing, "id", 3L);
        when(orderRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Order found = orderApplicationService.getOrder(3L);

        // Then
        assertThat(found.getId()).isEqualTo(3L);
        assertThat(found.getTotalPrice()).isEqualByComparingTo("30.00");
        verify(orderRepository).findById(3L);

        // 미존재 케이스 검증
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> orderApplicationService.getOrder(99L))
                .isInstanceOf(com.example.ecommerce.domain.exception.OrderNotFoundException.class);
        verify(orderRepository).findById(99L);
    }
}
