package com.shop.dto;

import lombok.Data;

@Data
public class OrderLineDto {

    private Long shopId;
    private Long productId;
    private Integer amount;
}
