package com.shop.service.impl;

import com.shop.dto.ProductDto;
import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.entity.Shop;
import com.shop.repository.ProductRepository;
import com.shop.repository.ShopRepository;
import com.shop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ShopRepository shopRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(ProductDto productRequest, Long shopId) {

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setAmount(productRequest.getAmount());
        product.setCategory(Category.valueOf(productRequest.getCategory()));

        if (shopId > 0) {
            Shop shop = shopRepository.findById(shopId)
                    .orElseThrow(() -> new IllegalArgumentException("Shop not found"));
            product.setShop(shop);
            shop.getMenu().add(product);
            shopRepository.save(shop);
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long productId, ProductDto productRequest) {
        if(!productRepository.existsById(productId))
            throw new IllegalArgumentException("Product not found");
        productRepository.findById(productId)
                .ifPresent(product -> {
                    if(productRequest.getName() != null)
                        product.setName(productRequest.getName());
                    if(productRequest.getDescription() != null)
                        product.setDescription(productRequest.getDescription());
                    if(productRequest.getPrice() != null)
                        product.setPrice(productRequest.getPrice());
                    if(productRequest.getAmount() != null)
                        product.setAmount(productRequest.getAmount());
                    if(productRequest.getCategory() != null)
                        product.setCategory(Category.valueOf(productRequest.getCategory()));
                    productRepository.save(product);
                });
    }

    @Override
    public void updateProductAmount(Long productId, int amount) {
        if(!productRepository.existsById(productId))
            throw new IllegalArgumentException("Product not found");
        productRepository.findById(productId)
                .ifPresent(product -> {
                    if(product.getAmount() < amount)
                        throw new IllegalArgumentException("Not enough product in stock");
                    product.setAmount(product.getAmount() - amount);
                    productRepository.save(product);
                });
    }

    @Override
    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId))
            throw new IllegalArgumentException("Nothing to delete");
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public Double getProductPrice(Long productId) {
        return productRepository.findById(productId)
                .map(Product::getPrice)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }

}
