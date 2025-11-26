package com.practice.shopping.domain.controller;

import com.practice.shopping.domain.model.Cart;
import com.practice.shopping.domain.repository.CartRepository;
import com.practice.shopping.domain.response.ApiResponse;
import com.practice.shopping.domain.service.cart.CartServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@Transactional
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final CartServiceImpl cartService;

    @GetMapping("/{id}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long id) {
        Cart cart = cartService.getCart(id);

        return ResponseEntity.ok(new ApiResponse("Success", cart));
    }

    @DeleteMapping("/{id}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id) {
        cartService.clearCart(id);

        return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", null));
    }

    @GetMapping("/{id}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id) {
        BigDecimal totalPrice = cartService.getTotalPrice(id);

        return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
    }
}
