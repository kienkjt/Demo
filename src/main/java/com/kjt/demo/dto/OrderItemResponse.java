package com.kjt.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double price;
    private Double subtotal;
}

