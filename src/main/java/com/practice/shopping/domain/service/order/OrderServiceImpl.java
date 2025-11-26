package com.practice.shopping.domain.service.order;

import com.practice.shopping.domain.dto.OrderDto;
import com.practice.shopping.domain.model.Order;

import java.util.List;

public interface OrderServiceImpl {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
