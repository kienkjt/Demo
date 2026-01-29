package com.kjt.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String status;
    private BigDecimal totalAmount;
    private Instant orderDate;
    private List<OrderItemResponse> items;
}

