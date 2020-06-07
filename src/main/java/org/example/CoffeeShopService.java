package org.example;

import org.example.domain.MenuItem;
import org.example.domain.Order;
import org.example.domain.OrderItem;
import org.example.repository.MenuItemRepository;
import org.example.repository.OrderItemRepository;
import org.example.repository.OrderRepository;
import org.example.web.CreateOrderDTO;
import org.example.web.OrderDto;
import org.example.web.UpdateOrderDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CoffeeShopService {
    @Inject
    private OrderItemRepository orderItemRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private MenuItemRepository menuItemRepository;

    @Transactional
    public OrderDto createOrder(CreateOrderDTO orderDto) {
        List<MenuItem> items = menuItemRepository.findByNameIn(orderDto.getOrderedItems()
                .stream()
                .map(CreateOrderDTO.MenuItemDTO::getName)
                .collect(Collectors.toList()));
        if(items.isEmpty()) throw new RuntimeException("No menu item selected");
        Order order = Order.builder()
                .customerName(orderDto.getCustomerName())
                .orderedAt(LocalDateTime.now())
                .build();
        orderRepository.persist(order);
        List<OrderItem> orderItems = items.stream()
                .map(item->OrderItem.builder()
                                .menuItem(item)
                                .name(item.getName())
                                .order(order)
                                .price(item.getPrice())
                                .build())
                .collect(Collectors.toList());
        orderItemRepository.persist(orderItems);
        return OrderDto.builder().id(order.getId())
                .customerName(order.getCustomerName())
                .orderedAt(order.getOrderedAt())
                .items(orderItems.stream()
                        .map(i-> OrderDto.OrderItemDto.builder()
                                .id(i.getId())
                                .name(i.getName())
                                .price(i.getPrice())
                                .build()).collect(Collectors.toList()))
                .totalPrice(orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    @Transactional
    public OrderDto updateOrder(UpdateOrderDTO orderDto) {
        List<MenuItem> items = menuItemRepository.findByNameIn(orderDto.getOrderedItems()
                .stream()
                .map(UpdateOrderDTO.MenuItemDTO::getName)
                .collect(Collectors.toList()));
        if(items.isEmpty()) throw new RuntimeException("No menu item selected");

        Order order = orderRepository.findById(orderDto.getId());
        if(order ==null) throw new RuntimeException("Order not exist");

        order.setCustomerName(orderDto.getCustomerName());
        orderRepository.persist(order);

        order.getItems().forEach(item -> orderItemRepository.delete(item));

        List<OrderItem> orderItems = items.stream()
                .map(item->OrderItem.builder()
                        .menuItem(item)
                        .name(item.getName())
                        .order(order)
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());
        orderItemRepository.persist(orderItems);

        return OrderDto.builder().id(order.getId())
                .customerName(order.getCustomerName())
                .orderedAt(order.getOrderedAt())
                .items(orderItems.stream()
                        .map(i-> OrderDto.OrderItemDto.builder()
                                .id(i.getId())
                                .name(i.getName())
                                .price(i.getPrice())
                                .build()).collect(Collectors.toList()))
                .totalPrice(orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    public List<OrderDto> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(order->OrderDto.builder().id(order.getId())
                    .customerName(order.getCustomerName())
                    .orderedAt(order.getOrderedAt())
                    .items(order.getItems().stream()
                        .map(i-> OrderDto.OrderItemDto.builder()
                                .id(i.getId())
                                .name(i.getName())
                                .price(i.getPrice())
                                .build()).collect(Collectors.toList()))
                    .totalPrice(order.getItems().stream()
                                .map(OrderItem::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                    .build())
                .collect(Collectors.toList());
    }
}
