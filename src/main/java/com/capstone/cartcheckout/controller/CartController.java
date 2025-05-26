package com.capstone.cartcheckout.controller;

import com.capstone.cartcheckout.dto.AddToCartRequest;
import com.capstone.cartcheckout.dto.CartDto;
import com.capstone.cartcheckout.dto.CheckoutRequest;
import com.capstone.cartcheckout.service.CartService;
import com.capstone.cartcheckout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CheckoutService checkoutService;

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddToCartRequest request) {
        CartDto updatedCart = cartService.addItem(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        return cartService.getCart(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable String userId, @PathVariable String productId) {
        CartDto updatedCart = cartService.removeItem(userId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{userId}/items")
    public ResponseEntity<CartDto> updateCartItemQuantity(@PathVariable String userId, @RequestBody AddToCartRequest request) {
        CartDto updatedCart = cartService.updateItemQuantity(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<String> checkout(@PathVariable String userId, @RequestBody CheckoutRequest checkoutRequest) {
        checkoutService.checkout(userId, checkoutRequest.getDeliveryAddress(), checkoutRequest.getPaymentMethod());
        return ResponseEntity.status(HttpStatus.OK).body("Checkout successful!");
    }
}
