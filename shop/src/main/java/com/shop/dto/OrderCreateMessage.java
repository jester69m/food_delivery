package com.shop.dto;

import com.shop.entity.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class OrderCreateMessage {

    private Long orderId;
    private String userEmail;
    private Set<OrderLine> orderLines;
    private LocalDateTime orderDate;
    private Double totalPrice;
}
