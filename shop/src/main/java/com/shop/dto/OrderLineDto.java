package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDto {

    private Long shopId;
    private Long productId;
    private int amount;
}
