package com.shop.unit.service;

import com.shop.dto.ProductDto;
import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.entity.Shop;
import com.shop.repository.ProductRepository;
import com.shop.repository.ShopRepository;
import com.shop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_WithShop() {
        ProductDto productDto = new ProductDto("Product Name", "Description", 100.0, 10, "MAIN_COURSES");
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setMenu(new HashSet<>());

        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        productService.createProduct(productDto, 1L);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productCaptor.capture());
        verify(shopRepository, times(1)).save(shop);

        Product savedProduct = productCaptor.getValue();
        assertEquals("Product Name", savedProduct.getName());
        assertEquals("Description", savedProduct.getDescription());
        assertEquals(100.0, savedProduct.getPrice());
        assertEquals(10, savedProduct.getAmount());
        assertEquals(Category.MAIN_COURSES, savedProduct.getCategory());
        assertEquals(shop, savedProduct.getShop());
    }

    @Test
    public void testUpdateProduct() {
        ProductDto productDto = new ProductDto("Updated Name", "Updated Description", 200.0, 20, "DRINKS");
        Product product = new Product();
        product.setId(1L);
        product.setName("Old Name");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.updateProduct(1L, productDto);

        assertEquals("Updated Name", product.getName());
        assertEquals("Updated Description", product.getDescription());
        assertEquals(200.0, product.getPrice());
        assertEquals(20, product.getAmount());
        assertEquals(Category.DRINKS, product.getCategory());

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProductAmount() {
        Product product = new Product();
        product.setId(1L);
        product.setAmount(50);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.updateProductAmount(1L, 10);

        assertEquals(40, product.getAmount());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllProducts() {
        productService.getAllProducts();
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertEquals(product, foundProduct);
    }

    @Test
    public void testGetProductPrice() {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Double price = productService.getProductPrice(1L);

        assertEquals(100.0, price);
    }

    @Test
    public void testExistsById() {
        when(productRepository.existsById(1L)).thenReturn(true);

        boolean exists = productService.existsById(1L);

        assertTrue(exists);
    }
}
