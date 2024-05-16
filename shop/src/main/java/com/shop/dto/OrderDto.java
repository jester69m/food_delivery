package com.shop.dto;

import com.shop.entity.OrderLine;
import lombok.Data;

import java.util.Set;

@Data
public class OrderDto {

    private Set<OrderLineDto> orderLines;
}

