// src/main/java/com/example/ecommerce/infrastructure/adapter/in/web/OrderController.java
package com.example.ecommerce.infrastructure.adapter.in.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.application.port.in.CreateOrderUseCase;
import com.example.ecommerce.application.port.in.GetOrderUseCase;
import com.example.ecommerce.domain.exception.OrderNotFoundException;
import com.example.ecommerce.domain.model.Order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetOrderUseCase getOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        Long id = createOrderUseCase.createOrder(
                new CreateOrderUseCase.CreateOrderCommand(
                        request.customerId(),
                        request.items().stream()
                                .map(i -> new CreateOrderUseCase.Item(i.productId(), i.productName(), i.unitPrice(), i.quantity()))
                                .toList()
                )
        );
        Order order = getOrderUseCase.getOrder(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        Order order = getOrderUseCase.getOrder(id);
        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    public record CreateOrderRequest(
            @NotNull Long customerId,
            @NotNull List<OrderItem> items
    ) {}

    public record OrderItem(
            @NotNull Long productId,
            @NotBlank String productName,
            @NotNull @Min(0) BigDecimal unitPrice,
            @Min(1) int quantity
    ) {}

    public record OrderResponse(Long id, String status, BigDecimal totalPrice) {
        public static OrderResponse from(Order o) {
            return new OrderResponse(o.getId(), o.getStatus().name(), o.getTotalPrice());
        }
    }

    public record ErrorResponse(String message) {}
}
