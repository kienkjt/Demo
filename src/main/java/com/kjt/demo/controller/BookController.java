package com.kjt.demo.controller;

import com.kjt.demo.dto.ApiResponse;
import com.kjt.demo.dto.BookCreateRequest;
import com.kjt.demo.dto.BookResponse;
import com.kjt.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookCreateRequest request) {
        BookResponse createdBook = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("message.book.created", createdBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable("id") Long id,
            @Valid @RequestBody BookCreateRequest request) {
        BookResponse updatedBook = bookService.updateBook(id, request);
        return ResponseEntity.ok(ApiResponse.success("message.book.updated", updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("message.book.deleted", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable("id") Long id) {
        BookResponse book = bookService.getBook(id);
        return ResponseEntity.ok(ApiResponse.success("message.book.retrieved", book));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(ApiResponse.success("message.book.listed", books));
    }
}
