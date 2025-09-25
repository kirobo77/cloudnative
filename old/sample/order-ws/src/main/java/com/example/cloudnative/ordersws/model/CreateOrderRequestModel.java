package com.example.cloudnative.ordersws.model;

import lombok.Data;

@Data
public class CreateOrderRequestModel {
    private String productId;
    private Integer qty;
    private Integer unitPrice;

//    private String userId;
}

