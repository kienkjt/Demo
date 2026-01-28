package com.kjt.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private Integer stockQuantity;
    private Instant createdAt;
    private Instant updatedAt;
}

