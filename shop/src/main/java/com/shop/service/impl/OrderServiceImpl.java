package com.shop.service.impl;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderLineDto;
import com.shop.dto.ProductDto;
import com.shop.entity.Order;
import com.shop.entity.OrderLine;
import com.shop.entity.Product;
import com.shop.exception.NotFoundException;
import com.shop.repository.OrderRepository;
import com.shop.service.OrderService;
import com.shop.service.ProductService;
import com.shop.service.ShopService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShopService shopService;
    private final ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElseThrow(() -> new NotFoundException("Order not found"));
    }

    public Order createOrder(OrderDto order) {
        Order newOrder = new Order();
        //TODO: Set current user id from security context
//        newOrder.setUserId();
        return setUpOrderWithValidation(order, newOrder);
    }

    public Order updateOrder(Long orderId, OrderDto order) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order not found");
        }
        Order newOrder = getOrderById(orderId);

        return setUpOrderWithValidation(order, newOrder);
    }

    @NotNull
    private Order setUpOrderWithValidation(OrderDto order, Order newOrder) {
        newOrder.setOrderDate(LocalDateTime.now());

        Set<OrderLine> orderLines = new HashSet<>();
        double price = 0.0;
        for (OrderLineDto lineDto : order.getOrderLines()) {
            OrderLine orderLine = new OrderLine();
            if (!shopService.existsById(lineDto.getShopId())) {
                throw new NotFoundException("Shop not found");
            }
            orderLine.setShopId(lineDto.getShopId());
            if (!productService.existsById(lineDto.getProductId())) {
                throw new NotFoundException("Product not found");
            }
            Product product = productService.getProductById(lineDto.getProductId());
            orderLine.setProductId(lineDto.getProductId());
            if (product.getAmount() < lineDto.getAmount()) {
                throw new IllegalArgumentException("Not enough product in stock");
            }
            product.setAmount(product.getAmount() - lineDto.getAmount());
            productService.updateProduct(product.getId(), new ProductDto(product.getName(), product.getDescription(), product.getPrice(), product.getAmount(), product.getCategory().name()));
            orderLine.setAmount(lineDto.getAmount());
            price += product.getPrice() * orderLine.getAmount();
//            orderLineRepository.save(orderLine);
            orderLines.add(orderLine);
        }
        newOrder.setOrderLines(orderLines);
        newOrder.setTotalPrice(price);
        return orderRepository.save(newOrder);
    }

    public void deleteOrder(Long orderId) {
        getOrderById(orderId);

        orderRepository.deleteById(orderId);
    }

}
