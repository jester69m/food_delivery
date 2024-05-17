package com.shop.unit.entity;

import com.shop.entity.Category;
import com.shop.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    public void testProduct() {
        long id = 1L;
        String name = "Test Product";
        String description = "desr";
        double price = 10.99;
        int stock = 100;
        Category category = Category.MAIN_COURSES;

        Product product = new Product(id, name, description, price, stock, category,null);

        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice(), 0.001);
        assertEquals(stock, product.getAmount());
        assertEquals(category, product.getCategory());

    }


}
