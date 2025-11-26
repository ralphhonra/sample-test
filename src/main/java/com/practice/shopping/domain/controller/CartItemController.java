package com.practice.shopping.domain.controller;

import com.practice.shopping.domain.response.ApiResponse;
import com.practice.shopping.domain.service.cart.CartItemServiceImpl;
import com.practice.shopping.domain.service.cart.CartServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Transactional
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final CartServiceImpl cartService;
    private final CartItemServiceImpl cartItemService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        if (cartId == null) {
            cartId= cartService.initializeNewCart();
        }
        cartItemService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long itemId
    ) {
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam Integer quantity
    ) {
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
    }
}
