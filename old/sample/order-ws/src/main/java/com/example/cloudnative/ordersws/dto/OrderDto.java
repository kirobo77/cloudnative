package com.example.cloudnative.ordersws.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
