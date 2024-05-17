package com.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class OrderLineMessage {

    private Long shopId;
    private Long productId;
    private int amount;

    @Override
    public String toString() {
        return "line{" +
                "shopId=" + shopId +
                ", productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
