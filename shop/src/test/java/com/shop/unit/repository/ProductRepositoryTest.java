package com.shop.unit.repository;

import com.shop.dto.ProductDto;
import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateProduct() {
        Product product = new Product(/* set necessary fields */);
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId());
    }

    @Test
    public void testReadProduct() {
        Product product = new Product(1L, "Test Name", "Test Description", 10.99, 100, Category.MAIN_COURSES, null);
        product = productRepository.save(product);

        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        assertTrue(optionalProduct.isPresent());
        assertEquals(optionalProduct.get().getName(), product.getName());
    }

    @Test
    public void testUpdateProduct() {
        long id = 1L;
        String name = "Test Name";
        String desr = "Test Description";
        double price = 10.99;
        int amount = 100;
        Category category = Category.MAIN_COURSES;

        Product product = new Product(id,name, desr, price, amount, category, null);
        product = productRepository.save(product);
        name = "Updated Name";
        product.setName(name);
        Product updatedProduct = productRepository.save(product);
        assertEquals(name, updatedProduct.getName());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product = productRepository.save(product);
        assertTrue(productRepository.findById(product.getId()).isPresent());

        productRepository.delete(product);
        assertFalse(productRepository.findById(product.getId()).isPresent());
    }
}