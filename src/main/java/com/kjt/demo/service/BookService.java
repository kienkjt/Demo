package com.kjt.demo.service;

import com.kjt.demo.dto.BookCreateRequest;
import com.kjt.demo.dto.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookCreateRequest request);

    BookResponse updateBook(Long id, BookCreateRequest request);

    void deleteBook(Long id);

    BookResponse getBook(Long id);

    List<BookResponse> getAllBooks();
}
