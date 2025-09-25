package com.example.cloudnative.ordersws.service;

import com.example.cloudnative.ordersws.dto.OrderDto;
import com.example.cloudnative.ordersws.entity.OrderEntity;

public interface OrdersService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
