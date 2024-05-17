package com.shop.unit.service;

import com.shop.dto.ShopDto;
import com.shop.entity.Shop;
import com.shop.repository.ShopRepository;
import com.shop.service.impl.ShopServiceImpl;
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

public class ShopServiceTests {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShop_Success() {
        ShopDto shopDto = new ShopDto("Shop Name", "Description", "Address", null);

        shopService.createShop(shopDto);

        ArgumentCaptor<Shop> shopCaptor = ArgumentCaptor.forClass(Shop.class);
        verify(shopRepository, times(1)).save(shopCaptor.capture());

        Shop savedShop = shopCaptor.getValue();
        assertEquals("Shop Name", savedShop.getName());
        assertEquals("Description", savedShop.getDescription());
        assertEquals("Address", savedShop.getAddress());
        assertEquals(new HashSet<>(), savedShop.getMenu());
    }

    @Test
    public void testCreateShop_ThrowsException_WhenNameIsNull() {
        ShopDto shopDto = new ShopDto(null, "Description", "Address", null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shopService.createShop(shopDto);
        });

        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    public void testCreateShop_ThrowsException_WhenAddressIsNull() {
        ShopDto shopDto = new ShopDto("Shop Name", "Description", null, null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shopService.createShop(shopDto);
        });

        assertEquals("Address is required", exception.getMessage());
    }

    @Test
    public void testGetAllShops() {
        shopService.getAllShops();
        verify(shopRepository, times(1)).findAll();
    }

    @Test
    public void testGetShopById_Success() {
        Shop shop = new Shop();
        shop.setId(1L);
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        Optional<Shop> foundShop = shopService.getShopById(1L);

        assertTrue(foundShop.isPresent());
        assertEquals(shop, foundShop.get());
    }

    @Test
    public void testGetShopById_NotFound() {
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Shop> foundShop = shopService.getShopById(1L);

        assertFalse(foundShop.isPresent());
    }

    @Test
    public void testUpdateShop_Success() {
        Shop shop = new Shop();
        shop.setId(1L);
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        ShopDto shopDto = new ShopDto("Updated Name", "Updated Description", "Updated Address", null);

        shopService.updateShop(1L, shopDto);

        assertEquals("Updated Name", shop.getName());
        assertEquals("Updated Description", shop.getDescription());
        assertEquals("Updated Address", shop.getAddress());

        verify(shopRepository, times(1)).save(shop);
    }

    @Test
    public void testUpdateShop_ThrowsException_WhenShopNotFound() {
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        ShopDto shopDto = new ShopDto("Updated Name", "Updated Description", "Updated Address", null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shopService.updateShop(1L, shopDto);
        });

        assertEquals("Shop not found", exception.getMessage());
    }

    @Test
    public void testDeleteShop_Success() {
        when(shopRepository.existsById(1L)).thenReturn(true);

        shopService.deleteShop(1L);

        verify(shopRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteShop_ThrowsException_WhenShopNotFound() {
        when(shopRepository.existsById(1L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shopService.deleteShop(1L);
        });

        assertEquals("Nothing to delete. Try again", exception.getMessage());
    }

    @Test
    public void testExistsById() {
        when(shopRepository.existsById(1L)).thenReturn(true);

        boolean exists = shopService.existsById(1L);

        assertTrue(exists);
    }
}