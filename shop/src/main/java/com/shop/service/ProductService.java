package com.shop.service;

import com.shop.dto.ProductDto;
import com.shop.entity.Product;
import jakarta.annotation.Nullable;

import java.util.List;

public interface ProductService {

    void createProduct(ProductDto productRequest, Long shopId);

    void createProduct(ProductDto productRequest);

    void updateProduct(Long productId, ProductDto productRequest);

    void deleteProduct(Long productId);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    Double getProductPrice(Long productId);

    boolean existsById(Long productId);

    void updateProductAmount(Long productId, int amount);
}

