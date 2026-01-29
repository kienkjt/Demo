package com.kjt.demo.service.impl;

import com.kjt.demo.dto.BookCreateRequest;
import com.kjt.demo.dto.BookResponse;
import com.kjt.demo.entity.Book;
import com.kjt.demo.exception.ResourceNotFoundException;
import com.kjt.demo.repository.BookRepository;
import com.kjt.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);
        book.setVersion(0);


        book.setCreatedAt(Instant.now());
        book.setUpdatedAt(Instant.now());

        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookCreateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        if (request.getStockQuantity() != null) {
            book.setStockQuantity(request.getStockQuantity());
        }
        book.setUpdatedAt(Instant.now());

        Book updatedBook = bookRepository.save(book);
        return mapToResponse(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BookResponse mapToResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setPrice(book.getPrice());
        response.setStockQuantity(book.getStockQuantity());
        response.setCreatedAt(book.getCreatedAt());
        response.setUpdatedAt(book.getUpdatedAt());
        return response;
    }
}
