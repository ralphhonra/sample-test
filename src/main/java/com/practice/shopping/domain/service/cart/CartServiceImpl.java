package com.practice.shopping.domain.service.cart;

import com.practice.shopping.domain.model.Cart;

import java.math.BigDecimal;

public interface CartServiceImpl {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
    Cart getCartByUserId(Long userId);
}
