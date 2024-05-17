package com.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class OrderCreateMessage {

    private Long orderId;
    private String userEmail;
    private Set<OrderLineMessage> orderLines;
    private LocalDateTime orderDate;
    private Double totalPrice;

    @Override
    public String toString() {
        return "OrderCreateMessage{" +
                "userEmail=" + userEmail +
                ", orderLines=" + orderLines +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
