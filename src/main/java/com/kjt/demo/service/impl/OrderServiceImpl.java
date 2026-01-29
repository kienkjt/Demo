package com.kjt.demo.service.impl;

import com.kjt.demo.dto.OrderCreateRequest;
import com.kjt.demo.dto.OrderItemRequest;
import com.kjt.demo.dto.OrderItemResponse;
import com.kjt.demo.dto.OrderResponse;
import com.kjt.demo.entity.*;
import com.kjt.demo.exception.InsufficientStockException;
import com.kjt.demo.exception.ResourceNotFoundException;
import com.kjt.demo.repository.BookRepository;
import com.kjt.demo.repository.CustomerRepository;
import com.kjt.demo.repository.OrderItemRepository;
import com.kjt.demo.repository.OrderRepository;
import com.kjt.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        if (!customerRepository.existsById(request.getCustomerId())) {
            throw new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId());
        }

        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(Instant.now());
        order.setStatus(OrderStatus.PENDING);
        order.setVersion(0);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());


        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

            if (book.getStockQuantity() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Not enough stock");
            }

            book.setStockQuantity(book.getStockQuantity() - itemRequest.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = new OrderItem();
            orderItem.setBookId(book.getId());
            orderItem.setQuantity(itemRequest.getQuantity());

            orderItem.setPrice(book.getPrice());

            BigDecimal subtotal = book.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(subtotal);
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        // Now save items with order ID
        for (OrderItem item : orderItems) {
            item.setOrderId(savedOrder.getId());
            orderItemRepository.save(item);
        }

        return mapToResponse(savedOrder, orderItems);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        List<OrderItem> items = orderItemRepository.findByOrderId(id);
        return mapToResponse(order, items);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
            return mapToResponse(order, items);
        }).collect(Collectors.toList());
    }

    private OrderResponse mapToResponse(Order order, List<OrderItem> items) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus().name());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());


        List<OrderItemResponse> itemResponses = items.stream().map(item -> {
            OrderItemResponse ir = new OrderItemResponse();
            ir.setBookId(item.getBookId());
            ir.setQuantity(item.getQuantity());
            ir.setPrice(BigDecimal.valueOf(item.getPrice().doubleValue()));
            ir.setSubtotal(BigDecimal.valueOf(item.getSubtotal().doubleValue()));
            bookRepository.findById(item.getBookId()).ifPresent(book -> ir.setBookTitle(book.getTitle()));
            return ir;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }
}
