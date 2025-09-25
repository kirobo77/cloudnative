// src/main/java/com/example/ecommerce/domain/model/Order.java
package com.example.ecommerce.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    public enum OrderStatus { CREATED, PAID, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 단순화를 위해 연관 대신 FK 보유
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLine> lines = new ArrayList<>();

    @Column(name = "total_price", precision = 18, scale = 2, nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.CREATED;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Order() {
    }

    public Order(Long customerId, List<OrderLine> lines) {
        this.customerId = customerId;
        this.lines = lines != null ? lines : new ArrayList<>();
        recalculateTotal();
    }

    public void recalculateTotal() {
        this.totalPrice = this.lines.stream()
                .map(OrderLine::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addLine(OrderLine line) {
        this.lines.add(line);
        recalculateTotal();
    }

    public void markPaid() {
        this.status = OrderStatus.PAID;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    // getters
    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public List<OrderLine> getLines() { return lines; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // setters (if needed)
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public void setLines(List<OrderLine> lines) { this.lines = lines; recalculateTotal(); }
    public void setStatus(OrderStatus status) { this.status = status; }

    @Embeddable
    public static class OrderLine {
        @Column(name = "product_id", nullable = false)
        private Long productId;

        @Column(name = "product_name", nullable = false)
        private String productName;

        @Column(name = "unit_price", precision = 18, scale = 2, nullable = false)
        private BigDecimal unitPrice;

        @Column(name = "quantity", nullable = false)
        private int quantity;

        public OrderLine() {}

        public OrderLine(Long productId, String productName, BigDecimal unitPrice, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public BigDecimal lineTotal() {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }

        public Long getProductId() { return productId; }
        public String getProductName() { return productName; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public int getQuantity() { return quantity; }
    }
}
