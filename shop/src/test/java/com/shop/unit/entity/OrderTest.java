package com.shop.unit.entity;

import com.shop.entity.Order;
import com.shop.entity.OrderLine;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void testOrder() {
        long id = 1L;
        String email = "user@mail.com";
        double totalPrice = 100.0;
        OrderLine orderLine1 = new OrderLine(1L,1L, 1L, 3);
        Set<OrderLine> orderLines = Set.of(
                orderLine1,
                new OrderLine(2L,1L, 2L, 2),
                new OrderLine(3L, 1L, 3L, 1)
        );
        LocalDateTime orderDate = LocalDateTime.now();

        Order order = new Order(id, email, orderLines, orderDate, totalPrice);

        assertEquals(id, order.getId());
        assertEquals(email, order.getUserEmail());
        assertEquals(totalPrice, order.getTotalPrice(), 0.001);
        OrderLine found = order.getOrderLines().iterator().next();
        assertEquals(orderLine1.getId(), found.getId());
        assertEquals(orderLine1.getShopId(), found.getShopId());
        assertEquals(orderLine1.getProductId(), found.getProductId());
        assertEquals(orderLine1.getAmount(), found.getAmount());
        assertEquals(orderLines, order.getOrderLines());
        assertEquals(orderDate, order.getOrderDate());
    }
}
