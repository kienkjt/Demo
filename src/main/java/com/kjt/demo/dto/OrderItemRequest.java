package com.kjt.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    @NotNull(message = "validation.order-item.book.required")
    private Long bookId;

    @Min(value = 1, message = "validation.order-item.quantity.min")
    private Integer quantity;
}
