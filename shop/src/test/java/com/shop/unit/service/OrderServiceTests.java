package com.shop.unit.service;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderLineDto;
import com.shop.entity.Category;
import com.shop.entity.Order;
import com.shop.entity.OrderLine;
import com.shop.entity.Product;
import com.shop.exception.NotFoundException;
import com.shop.repository.OrderRepository;
import com.shop.service.ProductService;
import com.shop.service.ShopService;
import com.shop.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShopService shopService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        "testUser", null, Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));
    }

    @Test
    public void testGetOrderById_Success() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1L);

        assertEquals(order, foundOrder);
    }

    @Test
    public void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    public void testCreateOrder_Success() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderLines(new HashSet <>());

        Order newOrder = new Order();
        newOrder.setUserEmail("testUser");
        newOrder.setOrderDate(LocalDateTime.now());

        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);

        Order createdOrder = orderService.createOrder(orderDto);

        assertEquals("testUser", createdOrder.getUserEmail());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testUpdateOrder_Success() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderLines(new HashSet<>());

        Order updatedOrder = new Order();
        updatedOrder.setUserEmail("testUser");
        updatedOrder.setOrderDate(LocalDateTime.now());

        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(1L, orderDto);

        assertEquals("testUser", result.getUserEmail());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testUpdateOrder_NotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        OrderDto orderDto = new OrderDto();

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            orderService.updateOrder(1L, orderDto);
        });

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    public void testDeleteOrder_Success() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteOrder_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    public void testSetUpOrderWithValidation_Success() {
        OrderDto orderDto = new OrderDto();
        OrderLineDto orderLineDto = new OrderLineDto(1L, 1L, 2);
        orderDto.setOrderLines(Set.of(orderLineDto));

        Product product = new Product();
        product.setId(1L);
        product.setPrice(10.0);
        product.setAmount(5);
        product.setCategory(Category.MAIN_COURSES);

        Order newOrder = new Order();
        newOrder.setOrderLines(Set.of(new OrderLine(1L,1L, 1L, 2)));
        newOrder.setTotalPrice(20.0);


        when(shopService.existsById(1L)).thenReturn(true);
        when(productService.existsById(1L)).thenReturn(true);
        when(productService.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);


        Order result = orderService.createOrder(orderDto);

        assertEquals(1, result.getOrderLines().size());
        assertEquals(20.0, result.getTotalPrice());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
