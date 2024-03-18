package com.example.cloudnative.usersws.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.cloudnative.usersws.model.OrderResponseModel;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@FeignClient(name="order-ws", fallbackFactory = OrdersFallbackFactory.class)
public interface OrderServiceClient {

    @GetMapping("/order-ms/users/{id}/orders")
    List<OrderResponseModel> getOrders(@PathVariable String id);

}

@Component
class OrdersFallbackFactory implements FallbackFactory<OrderServiceClient> {
    @Override
    public OrderServiceClient create(Throwable cause) {
        return new OrdersServiceClientFallback(cause);
    }
}

@Slf4j
class OrdersServiceClientFallback implements OrderServiceClient {
    private final Throwable cause;

    public OrdersServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<OrderResponseModel> getOrders(String id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place when getOrders was called with userId: " +
                    id + ". Error message: " + cause.getLocalizedMessage());
        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
        }

        return new ArrayList<>();
    }
}