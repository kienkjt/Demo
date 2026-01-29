package com.kjt.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {

    @NotNull(message = "validation.order.customer.required")
    private Long customerId;

    @NotEmpty(message = "validation.order.items.required")
    @NotNull(message = "validation.order.items.empty")
    @Valid
    private List<OrderItemRequest> items;
}

