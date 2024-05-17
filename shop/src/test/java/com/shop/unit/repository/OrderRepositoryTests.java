package com.shop.unit.repository;

import com.shop.entity.Order;
import com.shop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setUserEmail("test@example.com");
        order.setTotalPrice(100.0);
        Order savedOrder = orderRepository.save(order);

        assertNotNull(savedOrder.getId());
        assertEquals(order.getUserEmail(), savedOrder.getUserEmail());
        assertEquals(order.getTotalPrice(), savedOrder.getTotalPrice());
    }

    @Test
    public void testReadOrder() {
        Order order = new Order();
        order.setUserEmail("test@example.com");
        order.setTotalPrice(100.0);
        order = orderRepository.save(order);

        Optional<Order> found = orderRepository.findById(order.getId());
        assertTrue(found.isPresent());
    }

    @Test
    public void testUpdateOrder() {
        Order order = new Order();
        order.setUserEmail("test@example.com");
        order.setTotalPrice(100.0);
        order = orderRepository.save(order);
        order.setUserEmail("updated@example.com");
        Order updatedOrder = orderRepository.save(order);
        assertEquals("updated@example.com", updatedOrder.getUserEmail());
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order();
        order.setUserEmail("test@example.com");
        order.setTotalPrice(100.0);
        order = orderRepository.save(order);
        assertTrue(orderRepository.findById(order.getId()).isPresent());
        orderRepository.delete(order);
        assertFalse(orderRepository.findById(order.getId()).isPresent());
    }
}
