package com.shop.dto;

import com.shop.entity.Product;
import lombok.Data;

import java.util.Set;

@Data
public class ShopDto {

    private String name;
    private String description;
    private String address;
    private Set<Product> menu;
}
