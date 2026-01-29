package com.kjt.demo.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum OrderStatus {
    PENDING(1, "Chờ xác nhận"),
    CONFIRMED(2, "Đã xác nhận"),
    SHIPPING(3,"Đang giao hàng" ),
    DELIVERED(4,"Đã giao hàng" ),
    CANCELLED(5,"Đã hủy" );

    private final int value;
    private final String description;

    OrderStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public boolean canTransactionTo(OrderStatus orderStatus) {
        return getNextOrderStatus().contains(orderStatus);
    }

    public List<OrderStatus> getNextOrderStatus(){
        return switch (this){
            case PENDING -> Arrays.asList(CONFIRMED, DELIVERED);
            case CONFIRMED -> Arrays.asList(CANCELLED, SHIPPING);
            case SHIPPING -> List.of(DELIVERED);
            case DELIVERED -> List.of(CANCELLED);
            case CANCELLED -> List.of();
        };
    }
}
