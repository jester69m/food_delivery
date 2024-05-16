package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String description;
    private Double price;
    private Integer amount;
    private String category;
}
