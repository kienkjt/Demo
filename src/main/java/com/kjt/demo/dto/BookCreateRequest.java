package com.kjt.demo.dto;

import com.kjt.demo.validator.ISBN;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateRequest {
    @NotBlank(message = "validation.book.title.required")
    @Size(max = 255, message = "validation.book.title.size")
    private String title;

    @NotBlank(message = "validation.book.author.required")
    private String author;

    @NotBlank(message = "validation.book.isbn.required")
    @ISBN
    private String isbn;

    @NotNull(message = "validation.book.price.required")
    @Min(value = 0, message = "{validation.book.price.min}")
    private Double price;

    @Min(value = 0, message = "validation.book.stock.min")
    private Integer stockQuantity;
}
