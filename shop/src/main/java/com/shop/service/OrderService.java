package com.shop.service;

import com.shop.dto.OrderDto;
import com.shop.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order createOrder(OrderDto orderDto);

    Order getOrderById(Long orderId);

    Order updateOrder(Long orderId, OrderDto newOrder);

    void deleteOrder(Long orderId);
}
