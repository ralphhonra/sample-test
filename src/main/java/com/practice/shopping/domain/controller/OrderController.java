package com.practice.shopping.domain.controller;

import com.practice.shopping.domain.dto.OrderDto;
import com.practice.shopping.domain.model.Order;
import com.practice.shopping.domain.response.ApiResponse;
import com.practice.shopping.domain.service.order.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        Order order = orderService.placeOrder(userId);

        return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrder(orderId);

        return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        List<OrderDto> order = orderService.getUserOrders(userId);

        return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
    }
}
