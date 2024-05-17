package com.shop.unit.entity;

import com.shop.entity.Shop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopTest {

    @Test
    public void testShop() {
        long id = 1L;
        String name = "Test Shop";
        String desr = "Test Description";
        String address = "Test Address";
        Shop shop = new Shop(id, name, desr, address, null);
        assertEquals(id, shop.getId());
        assertEquals(name, shop.getName());
        assertEquals(desr, shop.getDescription());
        assertEquals(address, shop.getAddress());

    }
}
