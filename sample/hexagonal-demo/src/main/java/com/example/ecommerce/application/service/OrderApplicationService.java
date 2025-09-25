// src/main/java/com/example/ecommerce/application/service/OrderApplicationService.java
package com.example.ecommerce.application.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.application.port.in.CreateOrderUseCase;
import com.example.ecommerce.application.port.in.GetOrderUseCase;
import com.example.ecommerce.application.port.out.NotificationService;
import com.example.ecommerce.application.port.out.OrderRepository;
import com.example.ecommerce.application.port.out.PaymentService;
import com.example.ecommerce.domain.exception.OrderNotFoundException;
import com.example.ecommerce.domain.model.Order;
import com.example.ecommerce.domain.service.OrderDomainService;

@Service
@Transactional
public class OrderApplicationService implements CreateOrderUseCase, GetOrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final OrderDomainService orderDomainService;

    public OrderApplicationService(OrderRepository orderRepository,
                                   PaymentService paymentService,
                                   NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
        this.orderDomainService = new OrderDomainService();
    }

    @Override
    public Long createOrder(CreateOrderCommand command) {
        Order order = new Order(
                command.customerId(),
                command.items().stream()
                        .map(i -> new Order.OrderLine(i.productId(), i.productName(), i.unitPrice(), i.quantity()))
                        .collect(Collectors.toList())
        );
        orderDomainService.validate(order);
        orderDomainService.recalcTotal(order);

        Order saved = orderRepository.save(order);
        notificationService.notifyOrderCreated(saved.getId());

        var result = paymentService.processPayment(saved.getId(), saved.getTotalPrice());
        if (result.success()) {
            saved.markPaid();
            saved = orderRepository.save(saved);
            notificationService.notifyOrderPaid(saved.getId());
        }
        return saved.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
