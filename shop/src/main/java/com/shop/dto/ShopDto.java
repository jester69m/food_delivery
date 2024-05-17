package com.shop.dto;

import com.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {

    private String name;
    private String description;
    private String address;
    private Set<Product> menu;
}
