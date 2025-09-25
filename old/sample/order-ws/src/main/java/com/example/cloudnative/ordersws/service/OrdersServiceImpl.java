package com.example.cloudnative.ordersws.service;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.cloudnative.ordersws.dto.OrderDto;
import com.example.cloudnative.ordersws.entity.OrderEntity;
import com.example.cloudnative.ordersws.repository.OrdersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {
    OrdersRepository repository;

    Environment env;

    @Autowired
    public OrdersServiceImpl(OrdersRepository repository,
                             Environment env) {
        this.repository = repository;
        this.env = env;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = modelMapper.map(orderDetails, OrderEntity.class);
        orderEntity.setCreatedAt(new Date());
        
        repository.save(orderEntity);

        OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);
        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = repository.findByOrderId(orderId);

//        if (userEntity == null)
//            throw new UsernameNotFoundException("User not found");

        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}
