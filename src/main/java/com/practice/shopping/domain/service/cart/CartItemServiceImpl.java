package com.practice.shopping.domain.service.cart;

import com.practice.shopping.domain.model.CartItem;

public interface CartItemServiceImpl {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
