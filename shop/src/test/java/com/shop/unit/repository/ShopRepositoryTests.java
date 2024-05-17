package com.shop.unit.repository;

import com.shop.entity.Shop;
import com.shop.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ShopRepositoryTests {

    @Autowired
    private ShopRepository shopRepository;

    @Test
    public void testCreateShop() {
        Shop shop = new Shop();
        shop.setName("Test Shop");
        Shop savedShop = shopRepository.save(shop);
        assertNotNull(savedShop.getId());
        assertEquals(shop.getName(), savedShop.getName());
    }

    @Test
    public void testReadShop() {
        Shop shop = new Shop();
        shop.setName("Test Shop");
        shop = shopRepository.save(shop);

        Optional<Shop> optionalShop = shopRepository.findById(shop.getId());
        assertTrue(optionalShop.isPresent());
    }

    @Test
    public void testUpdateShop() {
        Shop shop = new Shop();
        shop.setName("Test Shop");
        shop = shopRepository.save(shop);
        shop.setName("Updated Shop Name");
        Shop updatedShop = shopRepository.save(shop);
        assertEquals("Updated Shop Name", updatedShop.getName());
    }

    @Test
    public void testDeleteShop() {
        Shop shop = new Shop();
        shop.setName("Test Shop");
        shop = shopRepository.save(shop);
        assertTrue(shopRepository.findById(shop.getId()).isPresent());
        shopRepository.delete(shop);
        assertFalse(shopRepository.findById(shop.getId()).isPresent());
    }
}
