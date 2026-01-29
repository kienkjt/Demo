package com.kjt.demo.service;

import com.kjt.demo.dto.OrderCreateRequest;
import com.kjt.demo.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request);

    OrderResponse getOrder(Long id);

    List<OrderResponse> getAllOrders();
}
