package com.capstone.cartcheckout.service;

import com.capstone.cartcheckout.dto.CartDto;

import java.util.Optional;

public interface CartService {
    CartDto addItem(String userId, String productId, int quantity);
    CartDto removeItem(String userId, String productId);
    CartDto updateItemQuantity(String userId, String productId, int quantity);
    Optional<CartDto> getCart(String userId);
}
